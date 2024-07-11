package net.lanet.forumhub.domain.resposta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.lanet.forumhub.infra.validation.NotBlankIfPresent;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaDtoUpdateRequest(

        @NotBlankIfPresent(message = "Mensagem precisa ser preenchido.")
        @JsonAlias("mensagem")
        String mensagem,

        @NotBlankIfPresent(message = "Id do Autor precisa ser informado.")
        @JsonAlias("autor_id")
        String autor_id,

        @NotBlankIfPresent(message = "Id do Tópico precisa ser informado.")
        @JsonAlias("topico_id")
        String topico_id
) {
}
