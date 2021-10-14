package academy.devdojo.springboot2.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter //Não entendi o motivo de utilizarmos essa anotação.
@SuperBuilder //Com essa anotação, poderemos utilizar o método "builder()" da superclasse "ExceptionDetails".
public class BadRequestExceptionDetails extends ExceptionDetails{ //Estamos herdando todos os atributos e métodos da superclasse "ExceptionDetails".

}
