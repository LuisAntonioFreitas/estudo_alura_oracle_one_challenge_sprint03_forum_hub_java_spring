package net.lanet.forumhub.domain.curso;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import net.lanet.forumhub.infra.validation.EnumValueConstraint;

//@Hidden // Swagger
@JsonIgnoreProperties(ignoreUnknown = true)
public record CursoDtoCreateRequest(
        @NotBlank(message = "Nome precisa ser preenchido.")
        @Size(max = 255, message = "Nome não pode conter mais do que 255 caracteres.")
        @JsonAlias("nome")
        String nome,

        @NotBlank(message = "Categoria precisa ser preenchida.")
        @EnumValueConstraint(enumClass = Categoria.class, message = "Categoria informada não é válida.")
        @JsonAlias("categoria")
        String categoria
) {
}
