package net.lanet.forumhub.domain.curso;

import net.lanet.forumhub.infra.utilities.exportfiles.IHandleExportFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICursoService extends IHandleExportFile {
    List<Curso> findAll(String search);
    List<Curso> findAllAtivoTrue(String search);
    Page<Curso> pageFindAll(Pageable page, String search);
    Page<Curso> pageFindAllAtivoTrue(Pageable page, String search);
    Optional<Curso> findById(String id);

    void delete(Curso item);
    void ativa(Curso item);
    Curso update(Curso item, CursoDtoUpdateRequest data);
    Curso create(CursoDtoCreateRequest data);
}
