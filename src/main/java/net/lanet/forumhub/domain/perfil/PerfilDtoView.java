package net.lanet.forumhub.domain.perfil;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record PerfilDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        Boolean ativo,
        String createdAt,
        String updatedAt
) {
    public PerfilDtoView(Perfil entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter)
        );
    }
}
