package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lanet.forumhub.domain.resposta.*;
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
@Tag(name = "Resposta") // Swagger
@SecurityRequirement(name = "bearer-key") // Swagger
@RequestMapping(path = "${api.config.path}")
public class RespostaController {
    @Autowired
    @Qualifier("respostaService")
    private IRespostaService service;

    @Autowired
    private ServiceCustom serviceCustom;

    private static final String ITEM = "resposta";
    private static final Object[] ITEMS = {"a","Resposta",ITEM};
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NUMBER = 0;


    @Operation(summary = "lista " + ITEM + "s") // Swagger
    @GetMapping(path = {"/topico/{id}/resposta/all"})
    public ResponseEntity<Object> findAll(HttpServletResponse response,
                                          @PathVariable(value = "id") String topicoId,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String export) {
//        if (verifyExport("All", topicoId, search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", topicoId, search, export, service, RespostaDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Resposta> listResult = service.findAll(topicoId, search);
        List<RespostaDtoViewList> viewList = ServiceCustom.listItens(listResult, RespostaDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos") // Swagger
    @GetMapping(path = {"/topico/{id}/resposta/all/ativo"})
    public ResponseEntity<Object> findAllAtivoTrue(HttpServletResponse response,
                                                   @PathVariable(value = "id") String topicoId,
                                                   @RequestParam(required = false) String search,
                                                   @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", topicoId, search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", topicoId, search, export, service, RespostaDtoViewList::new,
                response, ITEMS)) { return null; }

        List<Resposta> listResult = service.findAllAtivoTrue(topicoId, search);
        List<RespostaDtoViewList> viewList = ServiceCustom.listItens(listResult, RespostaDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s com paginação") // Swagger
    @GetMapping(path = {"/topico/{id}/resposta"})
    public ResponseEntity<Object> pageFindAll(@PageableDefault(page = PAGE_NUMBER, size = PAGE_SIZE,
                                              sort = {"dataCriacao"}, direction = Sort.Direction.DESC)
                                              Pageable page,
                                              HttpServletResponse response,
                                              @PathVariable(value = "id") String topicoId,
                                              @RequestParam(required = false) String search,
                                              @RequestParam(required = false) String export) {
//        if (verifyExport("All", topicoId, search, export, response)) { return null; }
        if (serviceCustom.verifyExport("All", topicoId, search, export, service, RespostaDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Resposta> listResult = service.pageFindAll(page, topicoId, search);
        Page<RespostaDtoViewList> viewList = ServiceCustom.pageListItens(listResult, RespostaDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + ITEM + "s ativos com paginação") // Swagger
    @GetMapping(path = {"/topico/{id}/resposta/ativo"})
    public ResponseEntity<Object> pageFindAllAtivoTrue(@PageableDefault(page = PAGE_NUMBER, size = PAGE_SIZE,
                                                       sort = {"dataCriacao"}, direction = Sort.Direction.DESC)
                                                       Pageable page,
                                                       HttpServletResponse response,
                                                       @PathVariable(value = "id") String topicoId,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) String export) {
//        if (verifyExport("Ativo", topicoId, search, export, response)) { return null; }
        if (serviceCustom.verifyExport("Ativo", topicoId, search, export, service, RespostaDtoViewList::new,
                response, ITEMS)) { return null; }

        Page<Resposta> listResult = service.pageFindAllAtivoTrue(page, topicoId, search);
        Page<RespostaDtoViewList> viewList = ServiceCustom.pageListItens(listResult, RespostaDtoViewList::new);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "detalha " + ITEM) // Swagger
    @GetMapping(path = {"/resposta/{id}"})
    public ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
//        Resposta item = findItem(id);
        Resposta item = ServiceCustom.findItem(id, service, ITEMS);
        RespostaDtoView view = new RespostaDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }


    @Operation(summary = "desativa " + ITEM) // Swagger
    @DeleteMapping(path = {"/resposta/{id}"})
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
//        Resposta item = findItem(id);
        Resposta item = ServiceCustom.findItem(id, service, ITEMS);
        service.delete(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "ativa " + ITEM) // Swagger
    @PatchMapping(path = {"/resposta/ativo/{id}"})
    public ResponseEntity<Object> ativa(@PathVariable(value = "id") String id) {
//        Resposta item = findItem(id);
        Resposta item = ServiceCustom.findItem(id, service, ITEMS);
        service.ativa(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "solução " + ITEM) // Swagger
    @PatchMapping(path = {"/resposta/solucao/{id}"})
    public ResponseEntity<Object> solucao(@PathVariable(value = "id") String id) {
//        Resposta item = findItem(id);
        Resposta item = ServiceCustom.findItem(id, service, ITEMS);
        service.solucao(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "atualiza " + ITEM) // Swagger
    @PutMapping(path = {"/resposta/{id}"})
    public ResponseEntity<Object> update(@PathVariable(value = "id") String id,
                                         @RequestBody @Valid RespostaDtoUpdateRequest data) {
//        Resposta item = service.update(findItem(id), data);
        Resposta item = service.update(ServiceCustom.findItem(id, service, ITEMS), data);
        RespostaDtoView view = new RespostaDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "cadastra " + ITEM) // Swagger
    @PostMapping(path = {"/resposta"})
    public ResponseEntity<Object> create(@RequestBody @Valid RespostaDtoCreateRequest data,
                                         WebRequest request, UriComponentsBuilder uriBuilder) {
        Resposta item = service.create(data);
        RespostaDtoView view = new RespostaDtoView(item);
        URI uri = UriBuilderUtil.getUri(item.getId(), request, uriBuilder);

        return ResponseEntity.created(uri).body(view);
    }


//    private Resposta findItem(String id) {
//        Optional<Resposta> optional = service.findById(id);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }
//    private List<RespostaDtoViewList> listItens(List<Resposta> list) {
//        List<RespostaDtoViewList> viewList = list
//                .stream()
//                .map(item -> new RespostaDtoViewList(item))
//                .collect(Collectors.toList());
//        return viewList;
//    }
//    private Page<RespostaDtoViewList> pageListItens(Page<Resposta> list) {
//        Page<RespostaDtoViewList> viewList = list
//                .map(item -> new RespostaDtoViewList(item));
//        return viewList;
//    }


//    private Boolean verifyExport(String type, String topicoId, String search, String export, HttpServletResponse response) {
//        if (export != null) {
//            String defineSearch = null;
//            if (search != null) { defineSearch = String.format("Search: %s", search); }
//            if (type.equalsIgnoreCase("Ativo")) {
//                if (defineSearch != null) { defineSearch = String.format("%s  |  Ativo: True", defineSearch); }
//                else { defineSearch = "Ativo: True"; }
//            }
//
//            List<Resposta> listResult = null;
//            if (type.equalsIgnoreCase("All")) {
//                listResult = service.findAll(topicoId, search);
//            }
//            if (type.equalsIgnoreCase("Ativo")) {
//                listResult = service.findAllAtivoTrue(topicoId, search);
//            }
//            List<RespostaDtoViewList> viewList = listItens(listResult);
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
