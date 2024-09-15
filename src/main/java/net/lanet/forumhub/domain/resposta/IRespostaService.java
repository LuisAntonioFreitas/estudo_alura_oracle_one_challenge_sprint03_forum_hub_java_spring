package net.lanet.forumhub.domain.resposta;

import net.lanet.forumhub.infra.shared.ServiceCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRespostaService extends ServiceCustom.MethodsStandard<Resposta> {
    List<Resposta> findAll(String topicoId, String search);
    List<Resposta> findAllAtivoTrue(String topicoId, String search);
    Page<Resposta> pageFindAll(Pageable page, String topicoId, String search);
    Page<Resposta> pageFindAllAtivoTrue(Pageable page, String topicoId, String search);
    Optional<Resposta> findById(String id);

    void delete(Resposta item);
    void ativa(Resposta item);
    void solucao(Resposta item);
    Resposta update(Resposta item, RespostaDtoUpdateRequest data);
    Resposta create(RespostaDtoCreateRequest data);
}
