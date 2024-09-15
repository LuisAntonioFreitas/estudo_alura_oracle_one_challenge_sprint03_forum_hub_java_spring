package net.lanet.forumhub.domain.topico;

import net.lanet.forumhub.infra.shared.ServiceCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITopicoService extends ServiceCustom.MethodsStandard<Topico> {
    List<Topico> findAll(String search);
    List<Topico> findAllAtivoTrue(String search);
    Page<Topico> pageFindAll(Pageable page, String search);
    Page<Topico> pageFindAllAtivoTrue(Pageable page, String search);
    Optional<Topico> findById(String id);

    List<Topico> findAllCurso(String search);
    List<Topico> findAllAtivoTrueCurso(String search);
    List<Topico> findAllCursoCategoria(String search);
    List<Topico> findAllAtivoTrueCursoCategoria(String search);
    List<Topico> findAllAutor(String search);
    List<Topico> findAllAtivoTrueAutor(String search);
    List<Topico> findAllAno(String search);
    List<Topico> findAllAtivoTrueAno(String search);

    Page<Topico> pageFindAllCurso(Pageable page, String search);
    Page<Topico> pageFindAllAtivoTrueCurso(Pageable page, String search);
    Page<Topico> pageFindAllCursoCategoria(Pageable page, String search);
    Page<Topico> pageFindAllAtivoTrueCursoCategoria(Pageable page, String search);
    Page<Topico> pageFindAllAutor(Pageable page, String search);
    Page<Topico> pageFindAllAtivoTrueAutor(Pageable page, String search);
    Page<Topico> pageFindAllAno(Pageable page, String search);
    Page<Topico> pageFindAllAtivoTrueAno(Pageable page, String search);

    void delete(Topico item);
    void ativa(Topico item);
    Topico update(Topico item, TopicoDtoUpdateRequest data);
    Topico create(TopicoDtoCreateRequest data);
}
