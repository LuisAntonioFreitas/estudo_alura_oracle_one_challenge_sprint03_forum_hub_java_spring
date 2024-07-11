package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.lanet.forumhub.domain.curso.CursoDtoView;
import net.lanet.forumhub.domain.usuario.UsuarioDtoView;
import net.lanet.forumhub.infra.utilities.DateTimeUtil;

@JsonPropertyOrder({"id"})
public record TopicoDtoView(
//        Long id,
        @JsonProperty("id")
        String uuid,
        String titulo,
        String mensagem,
        String dataCriacao,
        Status status,
        Boolean ativo,
        String createdAt,
        String updatedAt,
        UsuarioDtoView autor,
        CursoDtoView curso
) {
    public TopicoDtoView(Topico entity) {
        this(
//                entity.getId(),
                entity.getUuid(),
                entity.getTitulo(),
                entity.getMensagem(),
                entity.getDataCriacao().format(DateTimeUtil.formatter),
                entity.getStatus(),
                entity.getAtivo(),
                entity.getCreatedAt().format(DateTimeUtil.formatter),
                entity.getUpdatedAt().format(DateTimeUtil.formatter),
                new UsuarioDtoView(entity.getAutor()),
                new CursoDtoView(entity.getCurso())
        );
    }
}
