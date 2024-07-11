package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.curso.CursoDtoView;
import net.lanet.forumhub.domain.curso.CursoDtoViewList;
import net.lanet.forumhub.domain.usuario.UsuarioDtoView;
import net.lanet.forumhub.domain.usuario.UsuarioDtoViewList;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record TopicoDtoViewList(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String titulo,
        String mensagem,
        String dataCriacao,
        Status status,
        Boolean ativo,
        UsuarioDtoViewList autor,
        CursoDtoViewList curso
) {
    public TopicoDtoViewList(Topico entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getTitulo(),
                entity.getMensagem(),
                entity.getDataCriacao().format(DateTimeUtil.formatter),
                entity.getStatus(),
                entity.getAtivo(),
                new UsuarioDtoViewList(entity.getAutor()),
                new CursoDtoViewList(entity.getCurso())
        );
    }
}
