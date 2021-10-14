package academy.devdojo.springboot2.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{ //Essa classe será utilizada para criarmos uma exception personalizada, que será enviada quando não passaarmos argumentos suficientes para o objeto do tipo "AnimePostRequestBody" quando os parâmetros desse objeto estão sendo verificados através da anotação "@Valid" do framework "Spring Validator".
    private final String fields; //Esses atributos são os únicos atributos que serão retornados apenas pela exception do tipo "ValidationException".
    private final String fieldsMessage;
}
