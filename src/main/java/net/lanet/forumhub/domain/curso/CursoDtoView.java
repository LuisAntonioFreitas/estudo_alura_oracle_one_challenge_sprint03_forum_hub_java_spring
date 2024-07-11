package net.lanet.forumhub.domain.curso;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record CursoDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        Categoria categoria,
        Boolean ativo,
        String createdAt,
        String updatedAt
) {
    public CursoDtoView(Curso entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getCategoria(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter)
        );
    }
}
