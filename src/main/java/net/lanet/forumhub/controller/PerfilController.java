package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lanet.forumhub.domain.perfil.*;
import net.lanet.forumhub.infra.shared.ServiceCustom;
import net.lanet.forumhub.infra.utilities.UriBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "Perfil") // Swagger
@SecurityRequirement(name = "bearer-key") // Swagger
@RequestMapping(path = "${api.config.path}/perfil")
public class PerfilController {
    @Autowired
    @Qualifier("perfilService")
    private IPerfilService service;

    @Autowired
    private ServiceCustom serviceCustom;

    private static final String ITEM = "perfi";
    private static final Object[] ITEMS = {"o","Perfil",ITEM};
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NUMBER = 0;


    @Operation(summary = "lista " + ITEM + "s") // Swagger
    @GetMapping(path = {"/all"})
    public ResponseEntity<Object> findAll(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String export) {
//        if (verifyExport("All", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", search, export, service, PerfilDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Perfil> listResult = service.findAll(search);
        List<PerfilDtoViewList> viewList = ServiceCustom.listItens(listResult, PerfilDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos") // Swagger
    @GetMapping(path = {"/all/ativo"})
    public ResponseEntity<Object> findAllAtivoTrue(HttpServletResponse response,
                                                   @RequestParam(required = false) String search,
                                                   @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", search, export, service, PerfilDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Perfil> listResult = service.findAllAtivoTrue(search);
        List<PerfilDtoViewList> viewList = ServiceCustom.listItens(listResult, PerfilDtoViewList::new);

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
        if (serviceCustom.verifyExport("All", search, export, service, PerfilDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Perfil> listResult = service.pageFindAll(page, search);
        Page<PerfilDtoViewList> viewList = ServiceCustom.pageListItens(listResult, PerfilDtoViewList::new);

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
        if (serviceCustom.verifyExport("Ativo", search, export, service, PerfilDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Perfil> listResult = service.pageFindAllAtivoTrue(page, search);
        Page<PerfilDtoViewList> viewList = ServiceCustom.pageListItens(listResult, PerfilDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "detalha " + ITEM + "l") // Swagger
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
//        Perfil item = findItem(id);
        Perfil item = ServiceCustom.findItem(id, service, ITEMS);
        PerfilDtoView view = new PerfilDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "desativa " + ITEM + "l") // Swagger
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
//        Perfil item = findItem(id);
        Perfil item = ServiceCustom.findItem(id, service, ITEMS);
        service.delete(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "ativa " + ITEM + "l") // Swagger
    @PatchMapping(path = {"/ativo/{id}"})
    public ResponseEntity<Object> ativa(@PathVariable(value = "id") String id) {
//        Perfil item = findItem(id);
        Perfil item = ServiceCustom.findItem(id, service, ITEMS);
        service.ativa(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "atualiza " + ITEM + "l") // Swagger
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Object> update(@PathVariable(value = "id") String id,
                                         @RequestBody @Valid PerfilDtoUpdateRequest data) {
//        Perfil item = service.update(findItem(id), data);
        Perfil item = service.update(ServiceCustom.findItem(id, service, ITEMS), data);
        PerfilDtoView view = new PerfilDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "cadastra " + ITEM + "l") // Swagger
    @PostMapping(path = {""})
    public ResponseEntity<Object> create(@RequestBody @Valid PerfilDtoCreateRequest data,
                                         WebRequest request, UriComponentsBuilder uriBuilder) {
        Perfil item = service.create(data);
        PerfilDtoView view = new PerfilDtoView(item);
        URI uri = UriBuilderUtil.getUri(item.getId(), request, uriBuilder);

        return ResponseEntity.created(uri).body(view);
    }


//    private Perfil findItem(String id) {
//        Optional<Perfil> optional = service.findById(id);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }
//    private List<PerfilDtoViewList> listItens(List<Perfil> list) {
//        List<PerfilDtoViewList> viewList = list
//                .stream()
//                .map(item -> new PerfilDtoViewList(item))
//                .collect(Collectors.toList());
//        return viewList;
//    }
//    private Page<PerfilDtoViewList> pageListItens(Page<Perfil> list) {
//        Page<PerfilDtoViewList> viewList = list
//                .map(item -> new PerfilDtoViewList(item));
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
//            List<Perfil> listResult = null;
//            if (type.equalsIgnoreCase("All")) {
//                listResult = service.findAll(search);
//            }
//            if (type.equalsIgnoreCase("Ativo")) {
//                listResult = service.findAllAtivoTrue(search);
//            }
//            List<PerfilDtoViewList> viewList = listItens(listResult);
//            if (viewList.isEmpty()) {
//                throw new EntityNotFoundException("Não existe conteúdo a ser exportado.");
//            }
//            String name = RegexUtil.normalizeStringLettersAndNumbers(item);
//            List<Map<String, Object>> toListOfMaps = ConvertsDataUtil.convertToListOfMaps(viewList);
//            HandleExportFile.execute(export, service, response, toListOfMaps,
//                    String.format("%sList%s", name, type), String.format("Listagem | %s", item), defineSearch, name);
//
//            return true;
//        }
//        return false;
//    }

}
