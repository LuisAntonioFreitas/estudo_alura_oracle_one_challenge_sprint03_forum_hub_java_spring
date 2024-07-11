package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.perfil.PerfilDtoView;
import net.lanet.forumhub.domain.usuario.Usuario;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record TopicoRespostaUsuarioDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        String login,
        String email,
        Boolean ativo,
        String createdAt,
        String updatedAt
) {
    public TopicoRespostaUsuarioDtoView(Usuario entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getLogin(),
                entity.getEmail(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter)
        );
    }
}
