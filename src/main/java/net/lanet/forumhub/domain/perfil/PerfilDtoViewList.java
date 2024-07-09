package net.lanet.forumhub.domain.perfil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record PerfilDtoViewList(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        Boolean ativo
) {
    public PerfilDtoViewList(Perfil entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getAtivo()
        );
    }
}
