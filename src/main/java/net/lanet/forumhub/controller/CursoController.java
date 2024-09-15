package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lanet.forumhub.domain.curso.*;
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
@Tag(name = "Curso") // Swagger
@SecurityRequirement(name = "bearer-key") // Swagger
@RequestMapping(path = "${api.config.path}/curso")
public class CursoController {
    @Autowired
    @Qualifier("cursoService")
    private ICursoService service;

    @Autowired
    private ServiceCustom serviceCustom;

    private static final String ITEM = "curso";
    private static final Object[] ITEMS = {"o","Curso",ITEM};
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NUMBER = 0;


    @Operation(summary = "lista " + ITEM + "s") // Swagger
    @GetMapping(path = {"/all"})
    public ResponseEntity<Object> findAll(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String export) {
//        if (verifyExport("All", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", search, export, service, CursoDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Curso> listResult = service.findAll(search);
        List<CursoDtoViewList> viewList = ServiceCustom.listItens(listResult, CursoDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos") // Swagger
    @GetMapping(path = {"/all/ativo"})
    public ResponseEntity<Object> findAllAtivoTrue(HttpServletResponse response,
                                                   @RequestParam(required = false) String search,
                                                   @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", search, export, service, CursoDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Curso> listResult = service.findAllAtivoTrue(search);
        List<CursoDtoViewList> viewList = ServiceCustom.listItens(listResult, CursoDtoViewList::new);

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
        if (serviceCustom.verifyExport("All", search, export, service, CursoDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Curso> listResult = service.pageFindAll(page, search);
        Page<CursoDtoViewList> viewList = ServiceCustom.pageListItens(listResult, CursoDtoViewList::new);

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
        if (serviceCustom.verifyExport("Ativo", search, export, service, CursoDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Curso> listResult = service.pageFindAllAtivoTrue(page, search);
        Page<CursoDtoViewList> viewList = ServiceCustom.pageListItens(listResult, CursoDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "detalha " + ITEM) // Swagger
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
//        Curso item = findItem(id);
        Curso item = ServiceCustom.findItem(id, service, ITEMS);
        CursoDtoView view = new CursoDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "desativa " + ITEM) // Swagger
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
//        Curso item = findItem(id);
        Curso item = ServiceCustom.findItem(id, service, ITEMS);
        service.delete(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "ativa " + ITEM) // Swagger
    @PatchMapping(path = {"/ativo/{id}"})
    public ResponseEntity<Object> ativa(@PathVariable(value = "id") String id) {
//        Curso item = findItem(id);
        Curso item = ServiceCustom.findItem(id, service, ITEMS);
        service.ativa(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "atualiza " + ITEM) // Swagger
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Object> update(@PathVariable(value = "id") String id,
                                         @RequestBody @Valid CursoDtoUpdateRequest data) {
//        Curso item = service.update(findItem(id), data);
        Curso item = service.update(ServiceCustom.findItem(id, service, ITEMS), data);
        CursoDtoView view = new CursoDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "cadastra " + ITEM) // Swagger
    @PostMapping(path = {""})
    public ResponseEntity<Object> create(@RequestBody @Valid CursoDtoCreateRequest data,
                                         WebRequest request, UriComponentsBuilder uriBuilder) {
        Curso item = service.create(data);
        CursoDtoView view = new CursoDtoView(item);
        URI uri = UriBuilderUtil.getUri(item.getId(), request, uriBuilder);

        return ResponseEntity.created(uri).body(view);
    }


//    private Curso findItem(String id) {
//        Optional<Curso> optional = service.findById(id);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }
//    private List<CursoDtoViewList> listItens(List<Curso> list) {
//        List<CursoDtoViewList> viewList = list
//                .stream()
//                .map(item -> new CursoDtoViewList(item))
//                .collect(Collectors.toList());
//        return viewList;
//    }
//    private Page<CursoDtoViewList> pageListItens(Page<Curso> list) {
//        Page<CursoDtoViewList> viewList = list
//                .map(item -> new CursoDtoViewList(item));
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
//            List<Curso> listResult = null;
//            if (type.equalsIgnoreCase("All")) {
//                listResult = service.findAll(search);
//            }
//            if (type.equalsIgnoreCase("Ativo")) {
//                listResult = service.findAllAtivoTrue(search);
//            }
//            List<CursoDtoViewList> viewList = listItens(listResult);
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
