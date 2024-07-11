package net.lanet.forumhub.domain.curso;

import jakarta.servlet.http.HttpServletResponse;
import net.lanet.forumhub.infra.utilities.exportfiles.TemplateGenericExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.lanet.forumhub.infra.utilities.HandleFindIdOrUuidUtil.getIdOrUuid;

@Service
@Transactional
public class CursoService implements ICursoService {
    @Autowired
    private ICursoRepository repository;
    @Autowired
    private TemplateGenericExport template;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.ASC, "nome").ignoreCase());
        List<Curso> list;
        if (search == null) {
            list = repository.findAll(sortBy);
        } else {
            list = repository.findAllFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAllAtivoTrue(String search) {
        List<Curso> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByNomeAsc();
        } else {
            list = repository.findAllAtivoTrueFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Curso> pageFindAll(Pageable page, String search) {
        Page<Curso> list;
        if (search == null) {
            list = repository.findAll(page);
        } else {
            list = repository.pageFindAllFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Curso> pageFindAllAtivoTrue(Pageable page, String search) {
        Page<Curso> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByNomeAsc(page);
        } else {
            list = repository.pageFindAllAtivoTrueFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(String id) {
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Curso> item = repository.findFirstTop1ByIdOrUuid(longId, uuid);
        return item;
    }

    @Override
    public void delete(Curso item) {
        item.delete();
        repository.save(item);
    }

    @Override
    public void ativa(Curso item) {
        item.ativa();
        repository.save(item);
    }

    @Override
    public Curso update(Curso item, CursoDtoUpdateRequest data) {
        item.update(data);
        repository.save(item);
        return item;
    }

    @Override
    public Curso create(CursoDtoCreateRequest data) {
        Curso item = new Curso(data);
        repository.save(item);
        return item;
    }



    @Override
    public void generateXLS(HttpServletResponse response, List<Map<String, Object>> list, String fileName,
                            String title, String filter, String tabName) {
        // Excel
        template.generateXLS(response, list, fileName, title, filter, tabName);
    }
    @Override
    public void generateCSV(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
        template.generateCSV(response, list, fileName);
    }
    @Override
    public void generateTSV(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
        template.generateTSV(response, list, fileName);
    }
    @Override
    public void generatePDF(HttpServletResponse response, List<Map<String, Object>> list, String fileName) {
        template.generatePDF(response, list, fileName);
    }
}
