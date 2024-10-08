package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lanet.forumhub.domain.usuario.*;
import net.lanet.forumhub.infra.security.SecurityFilter;
import net.lanet.forumhub.infra.security.TokenService;
import net.lanet.forumhub.infra.shared.ServiceCustom;
import net.lanet.forumhub.infra.utilities.UriBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
@Tag(name = "Usuario") // Swagger
@SecurityRequirement(name = "bearer-key") // Swagger
@RequestMapping(path = "${api.config.path}/usuario")
public class UsuarioController {
    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService service;

    @Autowired
    private ServiceCustom serviceCustom;

    @Autowired
    private TokenService serviceToken;
    @Autowired
    private SecurityFilter securityFilter;

    private static final String ITEM = "usuario";
    private static final Object[] ITEMS = {"o","Usuário",ITEM};
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NUMBER = 0;


    @Operation(summary = "lista " + ITEM + "s") // Swagger
    @GetMapping(path = {"/all"})
    public ResponseEntity<Object> findAll(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String export) {
//        if (verifyExport("All", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", search, export, service, UsuarioDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Usuario> listResult = service.findAll(search);
        List<UsuarioDtoViewList> viewList = ServiceCustom.listItens(listResult, UsuarioDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos") // Swagger
    @GetMapping(path = {"/all/ativo"})
    public ResponseEntity<Object> findAllAtivoTrue(HttpServletResponse response,
                                                   @RequestParam(required = false) String search,
                                                   @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", search, export, service, UsuarioDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Usuario> listResult = service.findAllAtivoTrue(search);
        List<UsuarioDtoViewList> viewList = ServiceCustom.listItens(listResult, UsuarioDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s com paginação") // Swagger
    @GetMapping(path = {""})
    public ResponseEntity<Object> pageFindAll(@PageableDefault(page = PAGE_NUMBER, size = PAGE_SIZE,
                                              sort = {"nome"}, direction = Sort.Direction.ASC)
                                              Pageable page,
                                              HttpServletResponse response,
                                              @RequestParam(required = false) String search,
                                              @RequestParam(required = false) String export) {
//        if (verifyExport("All", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", search, export, service, UsuarioDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Usuario> listResult = service.pageFindAll(page, search);
        Page<UsuarioDtoViewList> viewList = ServiceCustom.pageListItens(listResult, UsuarioDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos com paginação") // Swagger
    @GetMapping(path = {"/ativo"})
    public ResponseEntity<Object> pageFindAllAtivoTrue(@PageableDefault(page = PAGE_NUMBER, size = PAGE_SIZE,
                                                       sort = {"nome"}, direction = Sort.Direction.ASC)
                                                       Pageable page,
                                                       HttpServletResponse response,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", search, export, service, UsuarioDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Usuario> listResult = service.pageFindAllAtivoTrue(page, search);
        Page<UsuarioDtoViewList> viewList = ServiceCustom.pageListItens(listResult, UsuarioDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "detalha " + ITEM) // Swagger
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
//        Usuario item = findItem(id);
        Usuario item = ServiceCustom.findItem(id, service, ITEMS);
        UsuarioDtoView view = new UsuarioDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "desativa " + ITEM) // Swagger
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
//        Usuario item = findItem(id);
        Usuario item = ServiceCustom.findItem(id, service, ITEMS);
        service.delete(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "ativa " + ITEM) // Swagger
    @PatchMapping(path = {"/ativo/{id}"})
    public ResponseEntity<Object> ativa(@PathVariable(value = "id") String id) {
//        Usuario item = findItem(id);
        Usuario item = ServiceCustom.findItem(id, service, ITEMS);
        service.ativa(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "atualiza " + ITEM) // Swagger
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Object> update(@PathVariable(value = "id") String id,
                                         @RequestBody @Valid UsuarioDtoUpdateRequest data) {
//        Usuario item = service.update(findItem(id), data);
        Usuario item = service.update(ServiceCustom.findItem(id, service, ITEMS), data);
        UsuarioDtoView view = new UsuarioDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "altera senha do " + ITEM + " logado") // Swagger
    @PatchMapping(path = {"/senha"})
    public ResponseEntity<Object> senha(@RequestBody @Valid UsuarioDtoSenhaRequest data,
                                        HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = securityFilter.recoverToken(request, response);

        Map<String, Object> dataToken = serviceToken.validateToken(token);
        String subject = dataToken.get("subject").toString();
        String id = dataToken.get("id").toString();

        if (!subject.trim().equalsIgnoreCase(ITEM)) {
            throw new BadCredentialsException("");
        }

//        Usuario item = service.senha(findItem(id), data);
        Usuario item = service.senha(ServiceCustom.findItem(id, service, ITEMS), data);
        if (item == null) {
            throw new BadCredentialsException("Nova Senha e Confirma Nova Senha não conferem.");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "cadastra " + ITEM) // Swagger
    @PostMapping(path = {""})
    public ResponseEntity<Object> create(@RequestBody @Valid UsuarioDtoCreateRequest data,
                                         WebRequest request, UriComponentsBuilder uriBuilder) {
        Usuario item = service.create(data);
        UsuarioDtoView view = new UsuarioDtoView(item);
        URI uri = UriBuilderUtil.getUri(item.getId(), request, uriBuilder);

        return ResponseEntity.created(uri).body(view);
    }

    @Operation(summary = "verifica credenciais do " + ITEM) // Swagger
    @PostMapping(path = {"/login"})
    public ResponseEntity<Object> login(@RequestBody @Valid UsuarioDtoLoginRequest data) {
        Usuario item = service.login(data.login(), data.senha());
        if (item == null) {
            throw new BadCredentialsException("");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }


//    private Usuario findItem(String id) {
//        Optional<Usuario> optional = service.findById(id);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }
//    private List<UsuarioDtoViewList> listItens(List<Usuario> list) {
//        List<UsuarioDtoViewList> viewList = list
//                .stream()
//                .map(item -> new UsuarioDtoViewList(item))
//                .collect(Collectors.toList());
//        return viewList;
//    }
//    private Page<UsuarioDtoViewList> pageListItens(Page<Usuario> list) {
//        Page<UsuarioDtoViewList> viewList = list
//                .map(item -> new UsuarioDtoViewList(item));
//        return viewList;
//    }


//    private Boolean verifyExport(String type, String search, String export, HttpServletResponse response) {
//        if (export != null) {
//            String defineSearch = null;
//            if (search != null) { defineSearch = String.format("Search: %s", search); }
//            if (type.equalsIgnoreCase("Ativo")) {
//                if (defineSearch != null) { defineSearch = String.format("%s  |  Ativo: True", defineSearch); }
//                else { defineSearch = "Ativo: True"; }
//            }
//
//            List<Usuario> listResult = null;
//            if (type.equalsIgnoreCase("All")) {
//                listResult = service.findAll(search);
//            }
//            if (type.equalsIgnoreCase("Ativo")) {
//                listResult = service.findAllAtivoTrue(search);
//            }
////            List<UsuarioDtoViewList> viewList = listItens(listResult);
//            List<UsuarioDtoViewList> viewList = ServiceCustom.listItens(listResult, UsuarioDtoViewList::new);
//            if (viewList.isEmpty()) {
//                throw new EntityNotFoundException("Não existe conteúdo a ser exportado.");
//            }
//            String name = RegexUtil.normalizeStringLettersAndNumbers(ITEM);
//            List<Map<String, Object>> toListOfMaps = ConvertsDataUtil.convertToListOfMaps(viewList);
//            HandleExportFile.execute(export, service, response, toListOfMaps,
//                    String.format("%sList%s", name, type), String.format("Listagem | %s", ITEM), defineSearch, name);
//
//            return true;
//        }
//        return false;
//    }

}
