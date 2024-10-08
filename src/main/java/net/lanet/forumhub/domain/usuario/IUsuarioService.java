package net.lanet.forumhub.domain.usuario;

import net.lanet.forumhub.infra.shared.ServiceCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService extends ServiceCustom.MethodsStandard<Usuario> {
    List<Usuario> findAll(String search);
    List<Usuario> findAllAtivoTrue(String search);
    Page<Usuario> pageFindAll(Pageable page, String search);
    Page<Usuario> pageFindAllAtivoTrue(Pageable page, String search);
    Optional<Usuario> findById(String id);

    void delete(Usuario item);
    void ativa(Usuario item);
    Usuario update(Usuario item, UsuarioDtoUpdateRequest data);
    Usuario senha(Usuario item, UsuarioDtoSenhaRequest data);
    Usuario create(UsuarioDtoCreateRequest data);

    Usuario login(String login, String senha);
}
