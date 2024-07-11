package net.lanet.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaDtoCreateRequest(

        @NotBlank(message = "Mensagem precisa ser preenchido.")
        @JsonAlias("mensagem")
        String mensagem,

        @NotBlank(message = "Id do Autor precisa ser informado.")
        @JsonAlias("autor_id")
        String autor_id,

        @NotBlank(message = "Id do Tópico precisa ser informado.")
        @JsonAlias("topico_id")
        String topico_id
) {
}
