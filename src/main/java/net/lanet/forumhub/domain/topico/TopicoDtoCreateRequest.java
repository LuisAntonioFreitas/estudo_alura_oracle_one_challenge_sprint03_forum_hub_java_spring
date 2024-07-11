package net.lanet.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import net.lanet.forumhub.infra.validation.EnumValueConstraint;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record TopicoDtoCreateRequest(
        @NotBlank(message = "Título precisa ser preenchido.")
        @Size(max = 255, message = "Título não pode conter mais do que 255 caracteres.")
        @JsonAlias("titulo")
        String titulo,

        @NotBlank(message = "Mensagem precisa ser preenchido.")
        @JsonAlias("mensagem")
        String mensagem,

        @NotBlank(message = "Status precisa ser preenchido.")
        @EnumValueConstraint(enumClass = Status.class, message = "Status informado não é válido.")
        @JsonAlias("status")
        String status,

        @NotBlank(message = "Id do Autor precisa ser informado.")
        @JsonAlias("autor_id")
        String autor_id,

        @NotBlank(message = "Id do Curso precisa ser informado.")
        @JsonAlias("curso_id")
        String curso_id
) {
}
