package net.lanet.forumhub.domain.curso;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id"})
public record CursoDtoViewList(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String nome,
        Categoria categoria,
        Boolean ativo
) {
    public CursoDtoViewList(Curso entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getNome(),
                entity.getCategoria(),
                entity.getAtivo()
        );
    }
}
