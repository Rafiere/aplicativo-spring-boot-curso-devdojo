package academy.devdojo.springboot2.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data //Gerando os métodos "getters", "setters" e dentre outros para os atributos dessa classe.
@SuperBuilder //Todas as classes que herdarem da classe "ExceptionDetails" também deverão utilizar a anotação "@SuperBuilder" para herdarem os atributos do método "builder()" da superclasse.
public class ExceptionDetails { //Criamos a superclasse "ExceptionDetails", dessa forma, através dessa classe, padronizaremos o retorno de todas as exceptions personalizadas, e cada exception personalizada, ou seja, cada subclasse dessa classe, poderá inserir a sua própria especialização na mensagem padrão que foi herdada dessa classe.
    protected String title;
    protected Integer status;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}
