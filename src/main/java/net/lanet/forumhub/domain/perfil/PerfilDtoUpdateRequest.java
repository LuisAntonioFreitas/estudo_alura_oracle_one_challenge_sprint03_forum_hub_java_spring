package net.lanet.forumhub.domain.perfil;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import net.lanet.forumhub.infra.validation.NotBlankIfPresent;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record PerfilDtoUpdateRequest(
        @NotBlankIfPresent(message = "Nome precisa ser preenchido.")
        @Size(max = 255, message = "Nome não pode conter mais do que 255 caracteres.")
        @JsonAlias("nome")
        String nome
) {
}
