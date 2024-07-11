package net.lanet.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lanet.forumhub.domain.curso.*;
import net.lanet.forumhub.domain.topico.*;
import net.lanet.forumhub.infra.utilities.ConvertsDataUtil;
import net.lanet.forumhub.infra.utilities.RegexUtil;
import net.lanet.forumhub.infra.utilities.UriBuilderUtil;
import net.lanet.forumhub.infra.utilities.exportfiles.HandleExportFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Tópico") // Swagger
@SecurityRequirement(name = "bearer-key") // Swagger
@RequestMapping(path = "${api.config.path}/topico")
public class TopicoController {
    @Autowired
    @Qualifier("topicoService")
    private ITopicoService service;

    private final String item = "Tópico";
    private final String itemLowerCase = "tópico";
    private final int pageSize = 10;
    private final int pageNumber = 0;


    @Operation(summary = "lista " + itemLowerCase + "s") // Swagger
    @GetMapping(path = {"/all"})
    public ResponseEntity<Object> findAll(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }

        List<Topico> listResult = service.findAll(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s ativos") // Swagger
    @GetMapping(path = {"/all/ativo"})
    public ResponseEntity<Object> findAllAtivoTrue(HttpServletResponse response,
                                                   @RequestParam(required = false) String search,
                                                   @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }

        List<Topico> listResult = service.findAllAtivoTrue(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s com paginação") // Swagger
    @GetMapping(path = {""})
    public ResponseEntity<Object> pageFindAll(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                              Pageable page,
                                              HttpServletResponse response,
                                              @RequestParam(required = false) String search,
                                              @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }

        Page<Topico> listResult = service.pageFindAll(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s ativos com paginação") // Swagger
    @GetMapping(path = {"/ativo"})
    public ResponseEntity<Object> pageFindAllAtivoTrue(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                       Pageable page,
                                                       HttpServletResponse response,
                                                       @RequestParam(required = false) String search,
                                                       @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }

        Page<Topico> listResult = service.pageFindAllAtivoTrue(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);

        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "detalha " + itemLowerCase) // Swagger
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Object> findById(@PathVariable(value = "id") String id) {
        Topico item = findItem(id);
        TopicoDtoView view = new TopicoDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }



    @Operation(summary = "lista " + itemLowerCase + "s com filtro") // Swagger
    @GetMapping(path = {"/all/curso/{search}"})
    public ResponseEntity<Object> findAllCurso(HttpServletResponse response,
                                               @PathVariable(value = "search") String search,
                                               @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllCurso(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com filtro") // Swagger
    @GetMapping(path = {"/all/ativo/curso/{search}"})
    public ResponseEntity<Object> findAllAtivoTrueCurso(HttpServletResponse response,
                                                        @PathVariable(value = "search") String search,
                                                        @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAtivoTrueCurso(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s com paginação e filtro") // Swagger
    @GetMapping(path = {"/curso/{search}"})
    public ResponseEntity<Object> pageFindAllCurso(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                   Pageable page,
                                                   HttpServletResponse response,
                                                   @PathVariable(value = "search") String search,
                                                   @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllCurso(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com paginação e filtro") // Swagger
    @GetMapping(path = {"/ativo/curso/{search}"})
    public ResponseEntity<Object> pageFindAllAtivoTrueCurso(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                            Pageable page,
                                                            HttpServletResponse response,
                                                            @PathVariable(value = "search") String search,
                                                            @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAtivoTrueCurso(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s com filtro") // Swagger
    @GetMapping(path = {"/all/cursocategoria/{search}"})
    public ResponseEntity<Object> findAllCursoCategoria(HttpServletResponse response,
                                                        @PathVariable(value = "search") String search,
                                                        @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllCursoCategoria(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com filtro") // Swagger
    @GetMapping(path = {"/all/ativo/cursocategoria/{search}"})
    public ResponseEntity<Object> findAllAtivoTrueCursoCategoria(HttpServletResponse response,
                                                        @PathVariable(value = "search") String search,
                                                        @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAtivoTrueCursoCategoria(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s com paginação e filtro") // Swagger
    @GetMapping(path = {"/cursocategoria/{search}"})
    public ResponseEntity<Object> pageFindAllCursoCategoria(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                   Pageable page,
                                                   HttpServletResponse response,
                                                   @PathVariable(value = "search") String search,
                                                   @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllCursoCategoria(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com paginação e filtro") // Swagger
    @GetMapping(path = {"/ativo/cursocategoria/{search}"})
    public ResponseEntity<Object> pageFindAllAtivoTrueCursoCategoria(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                                     Pageable page,
                                                                     HttpServletResponse response,
                                                                     @PathVariable(value = "search") String search,
                                                                     @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAtivoTrueCursoCategoria(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s com filtro") // Swagger
    @GetMapping(path = {"/all/autor/{search}"})
    public ResponseEntity<Object> findAllAutor(HttpServletResponse response,
                                               @PathVariable(value = "search") String search,
                                               @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAutor(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com filtro") // Swagger
    @GetMapping(path = {"/all/ativo/autor/{search}"})
    public ResponseEntity<Object> findAllAtivoTrueAutor(HttpServletResponse response,
                                                        @PathVariable(value = "search") String search,
                                                        @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAtivoTrueAutor(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s com paginação e filtro") // Swagger
    @GetMapping(path = {"/autor/{search}"})
    public ResponseEntity<Object> pageFindAllAutor(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                   Pageable page,
                                                   HttpServletResponse response,
                                                   @PathVariable(value = "search") String search,
                                                   @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAutor(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com paginação e filtro") // Swagger
    @GetMapping(path = {"/ativo/autor/{search}"})
    public ResponseEntity<Object> pageFindAllAtivoTrueAutor(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                            Pageable page,
                                                            HttpServletResponse response,
                                                            @PathVariable(value = "search") String search,
                                                            @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAtivoTrueAutor(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }

    @Operation(summary = "lista " + itemLowerCase + "s com filtro") // Swagger
    @GetMapping(path = {"/all/ano/{search}"})
    public ResponseEntity<Object> findAllAno(HttpServletResponse response,
                                             @PathVariable(value = "search") String search,
                                             @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAno(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com filtro") // Swagger
    @GetMapping(path = {"/all/ativo/ano/{search}"})
    public ResponseEntity<Object> findAllAtivoTrueAno(HttpServletResponse response,
                                                      @PathVariable(value = "search") String search,
                                                      @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        List<Topico> listResult = service.findAllAtivoTrueAno(search);
        List<TopicoDtoViewList> viewList = listItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s com paginação e filtro") // Swagger
    @GetMapping(path = {"/ano/{search}"})
    public ResponseEntity<Object> pageFindAllAno(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                 Pageable page,
                                                 HttpServletResponse response,
                                                 @PathVariable(value = "search") String search,
                                                 @RequestParam(required = false) String export) {
        if (verifyExport("All", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAno(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }
    @Operation(summary = "lista " + itemLowerCase + "s ativos com paginação e filtro") // Swagger
    @GetMapping(path = {"/ativo/ano/{search}"})
    public ResponseEntity<Object> pageFindAllAtivoTrueAno(@PageableDefault(page = pageNumber, size = pageSize, sort = {"dataCriacao"})
                                                          Pageable page,
                                                          HttpServletResponse response,
                                                          @PathVariable(value = "search") String search,
                                                          @RequestParam(required = false) String export) {
        if (verifyExport("Ativo", search, export, response)) { return null; }
        Page<Topico> listResult = service.pageFindAllAtivoTrueAno(page, search);
        Page<TopicoDtoViewList> viewList = pageListItens(listResult);
        return ResponseEntity.status(HttpStatus.OK).body(viewList);
    }



    @Operation(summary = "desativa " + itemLowerCase) // Swagger
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
        Topico item = findItem(id);
        service.delete(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "ativa " + itemLowerCase) // Swagger
    @PatchMapping(path = {"/ativo/{id}"})
    public ResponseEntity<Object> ativa(@PathVariable(value = "id") String id) {
        Topico item = findItem(id);
        service.ativa(item);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "atualiza " + itemLowerCase) // Swagger
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Object> update(@PathVariable(value = "id") String id,
                                         @RequestBody @Valid TopicoDtoUpdateRequest data) {
        Topico item = service.update(findItem(id), data);
        TopicoDtoView view = new TopicoDtoView(item);

        return ResponseEntity.status(HttpStatus.OK).body(view);
    }

    @Operation(summary = "cadastra " + itemLowerCase) // Swagger
    @PostMapping(path = {""})
    public ResponseEntity<Object> create(@RequestBody @Valid TopicoDtoCreateRequest data,
                                         WebRequest request, UriComponentsBuilder uriBuilder) {
        Topico item = service.create(data);
        TopicoDtoView view = new TopicoDtoView(item);
        URI uri = UriBuilderUtil.getUri(item.getId(), request, uriBuilder);

        return ResponseEntity.created(uri).body(view);
    }


    private Topico findItem(String id) {
        Optional<Topico> optional = service.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
        }
        return optional.get();
    }
    private List<TopicoDtoViewList> listItens(List<Topico> list) {
        List<TopicoDtoViewList> viewList = list
                .stream()
                .map(item -> new TopicoDtoViewList(item))
                .collect(Collectors.toList());
        return viewList;
    }
    private Page<TopicoDtoViewList> pageListItens(Page<Topico> list) {
        Page<TopicoDtoViewList> viewList = list
                .map(item -> new TopicoDtoViewList(item));
        return viewList;
    }


    private Boolean verifyExport(String type, String search, String export, HttpServletResponse response) {
        if (export != null) {
            String defineSearch = null;
            if (search != null) { defineSearch = String.format("Search: %s", search); }
            if (type.equalsIgnoreCase("Ativo")) {
                if (defineSearch != null) { defineSearch = String.format("%s  |  Ativo: True", defineSearch); }
                else { defineSearch = "Ativo: True"; }
            }

            List<Topico> listResult = null;
            if (type.equalsIgnoreCase("All")) {
                listResult = service.findAll(search);
            }
            if (type.equalsIgnoreCase("Ativo")) {
                listResult = service.findAllAtivoTrue(search);
            }
            List<TopicoDtoViewList> viewList = listItens(listResult);
            if (viewList.isEmpty()) {
                throw new EntityNotFoundException("Não existe conteúdo a ser exportado.");
            }
            String name = RegexUtil.normalizeStringLettersAndNumbers(item);
            List<Map<String, Object>> toListOfMaps = ConvertsDataUtil.convertToListOfMaps(viewList);
            HandleExportFile.execute(export, service, response, toListOfMaps,
                    String.format("%sList%s", name, type), String.format("Listagem | %s", item), defineSearch, name);

            return true;
        }
        return false;
    }

}
