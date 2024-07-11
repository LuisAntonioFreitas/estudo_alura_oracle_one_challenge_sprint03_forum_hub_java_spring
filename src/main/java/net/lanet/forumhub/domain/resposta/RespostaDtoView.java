package net.lanet.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.topico.TopicoDtoView;
import net.lanet.forumhub.domain.usuario.UsuarioDtoView;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record RespostaDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String mensagem,
        String dataCriacao,
        Boolean solucao,
        Boolean ativo,
        String createdAt,
        String updatedAt,
        UsuarioDtoView autor,
        RespostaTopicoDtoView topico
) {
    public RespostaDtoView(Resposta entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getMensagem(),
                entity.getDataCriacao().format(DateTimeUtil.formatter),
                entity.getSolucao(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter),
                new UsuarioDtoView(entity.getAutor()),
                new RespostaTopicoDtoView(entity.getTopico())
        );
    }
}
