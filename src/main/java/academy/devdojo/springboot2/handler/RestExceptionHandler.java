package academy.devdojo.springboot2.handler;

import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.exception.BadRequestExceptionDetails;
import academy.devdojo.springboot2.exception.ExceptionDetails;
import academy.devdojo.springboot2.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@ControllerAdvice //Ao utilizarmos essa anotação, sempre que uma determinada exception ocorrer nas classes "controller" da aplicação, o método que possuir a anotação "@ExceptionHandler" cujo valor do seu parâmetro for igual a classe da exception ocorrida, será executado.
public class RestExceptionHandler extends ResponseEntityExceptionHandler { //Essa classe será uma classe "Handler", ou seja, sempre que uma exception, que estiver sendo manipulada por essa classe, ocorrer, o método "handler" dessa determinada exception será executado, padronizando o objeto que será retornado quando essa exception ocorrer. Estamos herdando a classe "ResponseEntityExceptionHandler" para podermos sobrescrever o handler que é nativo do Spring, e, assim, padronizarmos os retornos das exceptions.
    @ExceptionHandler(BadRequestException.class)
    //Sempre que as classes "controller" gerarem uma exception do tipo "BadRequestException", o objeto retornado por essa requisição será construído dentro do método abaixo.
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>( //Estamos construindo o retorno da exception que será retornada por esse método, assim, poderemos padronizar o objeto que será retornado quando a exception "BadRequestException" ocorrer.
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception. Check the documentation!")
                        .details(badRequestException.getMessage())
                        .developerMessage(badRequestException.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override //Estamos sobrescrevendo o método "handleMethodArgumentNotValid()" da classe "ResponseEntityExceptionHandler", para, assim, padronizarmos o retorno desse tipo de exception.
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

        return new ResponseEntity<>( //Estamos construindo o retorno da exception que será retornada por esse método, assim, poderemos padronizar o objeto que será retornado quando a exception "MethodArgumentNotValidException" ocorrer.
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception. Invalid fields!")
                        .details(methodArgumentNotValidException.getMessage())
                        .developerMessage(methodArgumentNotValidException.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal( //Estamos sobrescrevendo o método "handleExceptionInternal()" da classe "ResponseEntityExceptionHandler", esse método, originalmente, serve para o Spring conseguir padronizar o retorno das exceptions que podem ocorrer durante uma aplicação, assim, ao sobrescrevermos esse método, poderemos criar o nosso próprio retorno, de forma personalizada, para as exceptions do Spring.
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
