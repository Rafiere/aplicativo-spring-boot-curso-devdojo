package academy.devdojo.springboot2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //Anotação que indica que essa classe ficará responsável por lançar uma exception que corresponderá ao código de erro HTTP "BAD_REQUEST".
public class BadRequestException extends RuntimeException { //Como a classe "BadRequestException" está herdando da classe "RuntimeException", a classe "BadRequestException" transformou-se em uma "unchecked exception".
    public BadRequestException(String mensagem){ //O método construtor dessa classe receberá o objeto "mensagem", que é do tipo "String", e passará esse objeto para a superclasse "RuntimeException", esse objeto do tipo "String" será a mensagem exibida quando essa exception ocorrer.
        super(mensagem);
    }
}
