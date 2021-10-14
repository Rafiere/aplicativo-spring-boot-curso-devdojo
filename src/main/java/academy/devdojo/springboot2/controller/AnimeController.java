package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import academy.devdojo.springboot2.service.AnimeService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController //Anotação que especifica que os métodos que estão dentro dessa classe podem ser serviços.
@RequestMapping("animes") //Para acessarmos os métodos que estão nesse "Controller", deveremos utilizar a url "http:localhost:8080/animes/enderecoDoMetodo". O nome do controller deverá, preferencialmente, estar no plural.
@Log4j2 //Anotação do "Lombok" que permite a utilização do "Log4j2". Não entendi muito bem essa anotação.
@RequiredArgsConstructor //Anotação do "Lombok" que gera um método construtor apenas com os atributos que possuam o modificador "final".
public class AnimeController {
    private final DateUtil dateUtil; //Não é recomendada a injeção de dependências via campos, através da anotação "@Autowired", dessa forma, estamos inserindo o modificador "final" nesse atributo para, através da anotação "@RequiredArgsConstructor", criarmos um método construtor apenas com os atributos que possuem o modificador "final" e realizarmos a injeção de dependências através do método construtor.
    private final AnimeService animeService;

    @GetMapping(path = "list") //O método abaixo poderá ser executado através de uma requisição "get" na URL abaixo.
    //URL (GET): http://127.0.0.1:8080/animes/list
    public ResponseEntity<List<Anime>> list(){ //Método que retornará uma lista com os animes abaixo.
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK); //Ao invés de retornarmos apenas o objeto no formato "JSON", estamos retornando um objeto do tipo "ResponseEntity", que é uma resposta HTTP completa, assim, dentro dessa resposta, estamos retornando, além da listagem de todos os objetos do tipo "Anime", um status "200", que é o status "OK" e que indica que a requisição foi retornada corretamente.
    }

    @GetMapping(path = "listPage") //O método abaixo poderá ser executado através de uma requisição "get" na URL abaixo.
    //URL (GET): http://127.0.0.1:8080/animes/list
    public ResponseEntity<Page<Anime>> list(Pageable pageable){ //Método que retornará uma lista com os animes abaixo.
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK); //Ao invés de retornarmos apenas o objeto no formato "JSON", estamos retornando um objeto do tipo "ResponseEntity", que é uma resposta HTTP completa, assim, dentro dessa resposta, estamos retornando, além da listagem de todos os objetos do tipo "Anime", um status "200", que é o status "OK" e que indica que a requisição foi retornada corretamente.
    }

    @GetMapping(path = "/{id}") //O "/{id}" significa que o valor do objeto "id", que é do tipo "Long", será passado através da URL.
    //URL (GET): http://127.0.0.1:8080/animes/id
    public ResponseEntity<Anime> findById(@PathVariable Long id){ //O valor que foi passado no espaço cujo nome é "id" da URL será inserido nesse objeto.
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK); //Ao invés de retornarmos apenas o objeto no formato "JSON", estamos retornando um objeto do tipo "ResponseEntity", que é uma resposta HTTP completa, assim, dentro dessa resposta, estamos retornando, além de um objeto do tipo "Anime", um status "200", que é o status "OK" e que indica que a requisição foi retornada corretamente.
    }

    @GetMapping(path = "/find")
    //URL (GET): http://127.0.0.1:8080/animes/find?nome={nomeDoAnime}"
    public ResponseEntity<List<Anime>> findByNome(@RequestParam String nome){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.findByNome(nome), HttpStatus.OK); //Método que retorna um objeto do tipo "ResponseEntity" que contém o objeto do tipo "List<Anime>" desejado e um status HTTP que indica que a requisição foi realizada com sucesso.
    }

    @PostMapping //Esse método será executado através de uma requisição HTTP do tipo "POST".
    //URL (POST - Passar JSON apenas com o objeto do tipo "Anime" com o atributo "nome" e o valor desse atributo): http://127.0.0.1:8080/animes
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) { //O objeto que será inserido no parâmetro "animePostRequestBody" será enviado através do "body" de uma requisição do tipo "POST" que será realizada na URL "http://127.0.0.1:8080/animes". A anotação "@Valid" obriga que o objeto recebido por esse método, que é do tipo "AnimePostRequestBody", possua o valor do atributo "nome" vazio ou "null", caso contrário, será lançada uma exception do próprio Spring.
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED); //Estamos retornando uma requisição que contém o retorno do método "save()" com o status HTTP "CREATED".
    }

    @DeleteMapping(path = "/{id}") //Esse método será executado através de uma requisição HTTP do tipo "DELETE".
    //URL (DELETE) http://127.0.0.1:8080/animes/id
    public ResponseEntity<Void> delete(@PathVariable Long id){ //O valor do parâmetro "id" será fornecido através da URL.
        animeService.delete(id); //Estamos chamando o método "delete()" e passando o atributo "id" para esse método.
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Após a execução do método "delete()", retornaremos o status HTTP "NO_CONTENT".
    }

    @PutMapping //Esse método será executado através de uma requisição HTTP do tipo "PUT".
    //URL (PUT - Passar JSON com um objeto do tipo "Anime" com os atributos "id" e "nome" e os valores desses atributos): http://127.0.0.1:8080/animes
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody); //Estamos chamando o método "replace()" e passando um objeto do tipo "AnimePutRequestBody" para esse método.
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
