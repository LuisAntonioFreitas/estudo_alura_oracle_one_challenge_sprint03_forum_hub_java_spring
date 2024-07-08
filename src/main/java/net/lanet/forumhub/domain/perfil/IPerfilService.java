package net.lanet.forumhub.domain.perfil;

import net.lanet.forumhub.infra.utilities.exportfiles.IHandleExportFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPerfilService extends IHandleExportFile {
    List<Perfil> findAll(String search);
    List<Perfil> findAllAtivoTrue(String search);
    Page<Perfil> pageFindAll(Pageable page, String search);
    Page<Perfil> pageFindAllAtivoTrue(Pageable page, String search);
    Optional<Perfil> findById(String id);

    void delete(Perfil item);
    void ativa(Perfil item);
    Perfil update(Perfil item, PerfilDtoUpdateRequest data);
    Perfil create(PerfilDtoCreateRequest data);
}
