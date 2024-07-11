package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import net.lanet.forumhub.infra.validation.EnumValueConstraint;
import net.lanet.forumhub.infra.validation.NotBlankIfPresent;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record TopicoDtoUpdateRequest(
        @NotBlankIfPresent(message = "Título precisa ser preenchido.")
        @Size(max = 255, message = "Título não pode conter mais do que 255 caracteres.")
        @JsonAlias("titulo")
        String titulo,

        @NotBlankIfPresent(message = "Mensagem precisa ser preenchido.")
        @JsonAlias("mensagem")
        String mensagem,

        @NotBlankIfPresent(message = "Status precisa ser preenchido.")
        @EnumValueConstraint(enumClass = Status.class, message = "Status informado não é válido.")
        @JsonAlias("status")
        String status,

        @NotBlankIfPresent(message = "Id do Autor precisa ser informado.")
        @JsonAlias("autor_id")
        String autor_id,

        @NotBlankIfPresent(message = "Id do Curso precisa ser informado.")
        @JsonAlias("curso_id")
        String curso_id
) {
}
