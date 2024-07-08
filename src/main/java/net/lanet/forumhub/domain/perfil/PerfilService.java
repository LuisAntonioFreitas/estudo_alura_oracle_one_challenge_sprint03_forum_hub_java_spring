package net.lanet.forumhub.domain.perfil;

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
public class PerfilService implements IPerfilService {
    @Autowired
    private IPerfilRepository repository;
    @Autowired
    private TemplateGenericExport template;

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> findAll(String search) {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.ASC, "nome").ignoreCase());
        List<Perfil> list;
        if (search == null) {
            list = repository.findAll(sortBy);
        } else {
            list = repository.findAllFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> findAllAtivoTrue(String search) {
        List<Perfil> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByNomeAsc();
        } else {
            list = repository.findAllAtivoTrueFilter(search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Perfil> pageFindAll(Pageable page, String search) {
        Page<Perfil> list;
        if (search == null) {
            list = repository.findAll(page);
        } else {
            list = repository.pageFindAllFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Perfil> pageFindAllAtivoTrue(Pageable page, String search) {
        Page<Perfil> list;
        if (search == null) {
            list = repository.findByAtivoTrueOrderByNomeAsc(page);
        } else {
            list = repository.pageFindAllAtivoTrueFilter(page, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> findById(String id) {
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Perfil> item = repository.findFirstTop1ByIdOrUuid(longId, uuid);
        return item;
    }

    @Override
    public void delete(Perfil item) {
        item.delete();
        repository.save(item);
    }

    @Override
    public void ativa(Perfil item) {
        item.ativa();
        repository.save(item);
    }

    @Override
    public Perfil update(Perfil item, PerfilDtoUpdateRequest data) {
        item.update(data);
        repository.save(item);
        return item;
    }

    @Override
    public Perfil create(PerfilDtoCreateRequest data) {
        Perfil item = new Perfil(data);
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
