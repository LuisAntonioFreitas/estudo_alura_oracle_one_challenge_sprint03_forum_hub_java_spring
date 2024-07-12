package net.lanet.forumhub.domain.resposta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import net.lanet.forumhub.domain.topico.*;
import net.lanet.forumhub.domain.usuario.IUsuarioRepository;
import net.lanet.forumhub.domain.usuario.Usuario;
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
public class RespostaService implements IRespostaService {
    @Autowired
    private IRespostaRepository repository;
    @Autowired
    private IUsuarioRepository repositoryAutor;
    @Autowired
    private ITopicoRepository repositoryTopico;
    @Autowired
    private TemplateGenericExport template;

    @Override
    @Transactional(readOnly = true)
    public List<Resposta> findAll(String topicoId, String search) {
        List<Resposta> list;
        if (search == null) {
            list = repository.findAllTopicoId(topicoId);
        } else {
            list = repository.findAllFilter(topicoId, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resposta> findAllAtivoTrue(String topicoId, String search) {
        List<Resposta> list;
        if (search == null) {
            list = repository.findAllAtivoTrueAndTopicoId(topicoId);
        } else {
            list = repository.findAllAtivoTrueFilter(topicoId, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Resposta> pageFindAll(Pageable page, String topicoId, String search) {
        Page<Resposta> list;
        if (search == null) {
            list = repository.findAllAtivoTrueAndTopicoId(page, topicoId);
        } else {
            list = repository.pageFindAllFilter(page, topicoId, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Resposta> pageFindAllAtivoTrue(Pageable page, String topicoId, String search) {
        Page<Resposta> list;
        if (search == null) {
            list = repository.findAllAtivoTrueAndTopicoId(page, topicoId);
        } else {
            list = repository.pageFindAllAtivoTrueFilter(page, topicoId, search);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resposta> findById(String id) {
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Resposta> item = repository.findFirstTop1ByIdOrUuid(longId, uuid);
        return item;
    }


    @Override
    public void delete(Resposta item) {
        item.delete();
        repository.save(item);
    }

    @Override
    public void ativa(Resposta item) {
        item.ativa();
        repository.save(item);
    }

    @Override
    public void solucao(Resposta item) {
        item.solucao();
        repository.save(item);
    }

    @Override
    public Resposta update(Resposta item, RespostaDtoUpdateRequest data) {
        Usuario autor = findAutorUpdate(data.autor_id(), item.getAutor());
        Topico topico = findTopicoUpdate(data.topico_id(), item.getTopico());
        item.update(data, autor, topico);
        repository.save(item);
        return item;
    }
    private Usuario findAutorUpdate(String id, Usuario autor) {
        if (id == null) { return autor; }
        return findAutorCreate(id);
    }
    private Topico findTopicoUpdate(String id, Topico topico) {
        if (id == null) { return topico; }
        return findTopicoCreate(id);
    }

    @Override
    public Resposta create(RespostaDtoCreateRequest data) {
        Usuario autor = findAutorCreate(data.autor_id());
        Topico topico = findTopicoCreate(data.topico_id());
        Resposta item = new Resposta(data, autor, topico);
        repository.save(item);
        return item;
    }
    private Usuario findAutorCreate(String id) {
        String item = "Autor";
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Usuario> optional = repositoryAutor.findFirstTop1ByIdOrUuid(longId, uuid);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
        }
        return optional.get();
    }
    private Topico findTopicoCreate(String id) {
        String item = "Tópico";
        Object[] value = getIdOrUuid(id);
        Long longId = (Long) value[0];
        String uuid = (String) value[1];
        Optional<Topico> optional = repositoryTopico.findFirstTop1ByIdOrUuid(longId, uuid);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String.format("%s não foi encontrado", item));
        }
        return optional.get();
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
