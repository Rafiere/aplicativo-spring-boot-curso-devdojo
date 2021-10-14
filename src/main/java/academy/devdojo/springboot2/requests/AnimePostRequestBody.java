package academy.devdojo.springboot2.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name cannot be empty or null.") //Essa anotação impede que o valor do atributo "nome" seja vazio ou "null".
    private String nome;
}
