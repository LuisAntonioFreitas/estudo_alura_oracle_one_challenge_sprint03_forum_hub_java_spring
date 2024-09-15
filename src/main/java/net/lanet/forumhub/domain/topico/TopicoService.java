package net.lanet.forumhub.domain.topico;

import net.lanet.forumhub.domain.curso.*;
import net.lanet.forumhub.domain.usuario.IUsuarioRepository;
import net.lanet.forumhub.domain.usuario.Usuario;
import net.lanet.forumhub.infra.shared.JpaRepositoryCustom;
import net.lanet.forumhub.infra.utilities.exportfiles.TemplateGenericExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static net.lanet.forumhub.infra.utilities.HandleFindIdOrUuidUtil.getIdOrUuid;

@Service
@Transactional
public class TopicoService implements ITopicoService {
    @Autowired
    private ITopicoRepository repository;
    @Autowired
    private IUsuarioRepository repositoryAutor;
    @Autowired
    private ICursoRepository repositoryCurso;
//    @Autowired
//    private TemplateGenericExport template;

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAll(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "dataCriacao").ignoreCase());
        List<Topico> list;
        if (search == null) {
            list = repository.findAll(sortBy);
        } else {
            list = repository.findAllFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAtivoTrue(String search) {
        List<Topico> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByDataCriacaoDesc();
        } else {
            list = repository.findAllAtivoTrueFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAll(Pageable page, String search) {
        Page<Topico> list;
        if (search == null) {
            list = repository.findAll(page);
        } else {
            list = repository.pageFindAllFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAtivoTrue(Pageable page, String search) {
        Page<Topico> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByDataCriacaoDesc(page);
        } else {
            list = repository.pageFindAllAtivoTrueFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Topico> findById(String id) {
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Topico> item = repository.findFirstTop1ByIdOrUuid(longId, uuid);
        return item;
    }



    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllCurso(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "dataCriacao").ignoreCase());
        List<Topico> list;
        list = repository.findAllFilterCurso(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAtivoTrueCurso(String search) {
        List<Topico> list;
        list = repository.findAllAtivoTrueFilterCurso(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllCursoCategoria(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "dataCriacao").ignoreCase());
        List<Topico> list;
        list = repository.findAllFilterCursoCategoria(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAtivoTrueCursoCategoria(String search) {
        List<Topico> list;
        list = repository.findAllAtivoTrueFilterCursoCategoria(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAutor(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "dataCriacao").ignoreCase());
        List<Topico> list;
        list = repository.findAllFilterAutor(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAtivoTrueAutor(String search) {
        List<Topico> list;
        list = repository.findAllAtivoTrueFilterAutor(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAno(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "dataCriacao").ignoreCase());
        List<Topico> list;
        list = repository.findAllFilterAno(search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAllAtivoTrueAno(String search) {
        List<Topico> list;
        list = repository.findAllAtivoTrueFilterAno(search);
        return list;
    }



    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllCurso(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllFilterCurso(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAtivoTrueCurso(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllAtivoTrueFilterCurso(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllCursoCategoria(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllFilterCursoCategoria(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAtivoTrueCursoCategoria(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllAtivoTrueFilterCursoCategoria(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAutor(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllFilterAutor(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAtivoTrueAutor(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllAtivoTrueFilterAutor(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAno(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllFilterAno(page, search);
        return list;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Topico> pageFindAllAtivoTrueAno(Pageable page, String search) {
        Page<Topico> list;
        list = repository.pageFindAllAtivoTrueFilterAno(page, search);
        return list;
    }




    @Override
    public void delete(Topico item) {
        item.delete();
        repository.save(item);
    }

    @Override
    public void ativa(Topico item) {
        item.ativa();
        repository.save(item);
    }

    @Override
    public Topico update(Topico item, TopicoDtoUpdateRequest data) {
//        Usuario autor = findAutorUpdate(data.autor_id(), item.getAutor());
//        Curso curso = findCursoUpdate(data.curso_id(), item.getCurso());
        Usuario autor = JpaRepositoryCustom.findEntityNotNull(
                data.autor_id(), item.getAutor(), repositoryAutor, new Object[] {"o","Autor"});
        Curso curso = JpaRepositoryCustom.findEntityNotNull(
                data.curso_id(), item.getCurso(), repositoryCurso, new Object[] {"o","Curso"});
        item.update(data, autor, curso);
        repository.save(item);
        return item;
    }
//    private Usuario findAutorUpdate(String id, Usuario autor) {
//        if (id == null) { return autor; }
//        return findAutorCreate(id);
//    }
//    private Curso findCursoUpdate(String id, Curso curso) {
//        if (id == null) { return curso; }
//        return findCursoCreate(id);
//    }

    @Override
    public Topico create(TopicoDtoCreateRequest data) {
//        Usuario autor = findAutorCreate(data.autor_id());
//        Curso curso = findCursoCreate(data.curso_id());
        Usuario autor = JpaRepositoryCustom.findEntityNotNull(
                data.autor_id(), null, repositoryAutor, new Object[] {"o","Autor"});
        Curso curso = JpaRepositoryCustom.findEntityNotNull(
                data.curso_id(), null, repositoryCurso, new Object[] {"o","Curso"});
        Topico item = new Topico(data, autor, curso);
        repository.save(item);
        return item;
    }
//    private Usuario findAutorCreate(String id) {
//        String item = "Autor";
//        Object[] value = getIdOrUuid(id);
//        Long longId = (Long) value[0];
//        String uuid = (String) value[1];
//        Optional<Usuario> optional = repositoryAutor.findFirstTop1ByIdOrUuid(longId, uuid);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }
//    private Curso findCursoCreate(String id) {
//        String item = "Curso";
//        Object[] value = getIdOrUuid(id);
//        Long longId = (Long) value[0];
//        String uuid = (String) value[1];
//        Optional<Curso> optional = repositoryCurso.findFirstTop1ByIdOrUuid(longId, uuid);
//        if (optional.isEmpty()) {
//            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
//        }
//        return optional.get();
//    }



//    @Override
//    public void generateXLS(HttpServletResponse response, List<Map<String, Object>> list, String fileName,
//                            String title, String filter, String tabName) {
//        // Excel
//        template.generateXLS(response, list, fileName, title, filter, tabName);
//    }
//    @Override
//    public void generateCSV(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
//        template.generateCSV(response, list, fileName);
//    }
//    @Override
//    public void generateTSV(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
//        template.generateTSV(response, list, fileName);
//    }
//    @Override
//    public void generatePDF(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
//        template.generatePDF(response, list, fileName);
//    }
}
