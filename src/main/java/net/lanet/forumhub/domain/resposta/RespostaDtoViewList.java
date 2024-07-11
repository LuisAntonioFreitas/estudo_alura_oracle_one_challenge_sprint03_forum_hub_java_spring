package net.lanet.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.topico.TopicoDtoViewList;
import net.lanet.forumhub.domain.usuario.UsuarioDtoViewList;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record RespostaDtoViewList(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String mensagem,
        String dataCriacao,
        Boolean solucao,
        Boolean ativo,
        UsuarioDtoViewList autor
) {
    public RespostaDtoViewList(Resposta entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getMensagem(),
                entity.getDataCriacao().format(DateTimeUtil.formatter),
                entity.getSolucao(),
                entity.getAtivo(),
                new UsuarioDtoViewList(entity.getAutor())
        );
    }
}
