package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.resposta.Resposta;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record TopicoRespostaDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String mensagem,
        String dataCriacao,
        Boolean solucao,
        Boolean ativo,
        String createdAt,
        String updatedAt,
        TopicoRespostaUsuarioDtoView autor
) {
    public TopicoRespostaDtoView(Resposta entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getMensagem(),
                entity.getDataCriacao().format(DateTimeUtil.formatter),
                entity.getSolucao(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter),
                new TopicoRespostaUsuarioDtoView(entity.getAutor())
        );
    }
}
