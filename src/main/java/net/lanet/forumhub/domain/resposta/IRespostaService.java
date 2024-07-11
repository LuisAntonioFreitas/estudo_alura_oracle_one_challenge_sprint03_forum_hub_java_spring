package net.lanet.forumhub.domain.resposta;

import net.lanet.forumhub.infra.utilities.exportfiles.IHandleExportFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRespostaService extends IHandleExportFile {
    List<Resposta> findAll(String topico_id, String search);
    List<Resposta> findAllAtivoTrue(String topico_id, String search);
    Page<Resposta> pageFindAll(Pageable page, String topico_id, String search);
    Page<Resposta> pageFindAllAtivoTrue(Pageable page, String topico_id, String search);
    Optional<Resposta> findById(String id);

    void delete(Resposta item);
    void ativa(Resposta item);
    void solucao(Resposta item);
    Resposta update(Resposta item, RespostaDtoUpdateRequest data);
    Resposta create(RespostaDtoCreateRequest data);
}
