package net.lanet.forumhub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.perfil.Perfil;
import net.lanet.forumhub.domain.perfil.PerfilDtoView;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id"})
public record UsuarioDtoViewList(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        String login,
        String email,
        Boolean ativo,
        String createdAt,
        String updatedAt,
        @JsonIgnore
        Perfil entityPerfil,
        PerfilDtoView perfil
) {
    public UsuarioDtoViewList(Usuario entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getLogin(),
                entity.getEmail(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter),
                entity.getPerfil(),
                new PerfilDtoView(entity.getPerfil()));
    }
}
