package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient { //Dentro dessa classe, realizaremos chamadas para a nossa própria API, simulando que somos uma aplicação externa que está realizando requisições.
    public static void main(String[] args){

        //Realizando uma requisição HTTP do tipo "GET" em nossa própria API, com o objetivo de obter um objeto do tipo "ResponseEntity<Anime>".

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://127.0.0.1:8080/animes/{id}", Anime.class, 15); //Estamos realizando uma requisição HTTP do tipo "GET" na URL "http://localhost:8080/animes/{id}", que retornará um objeto do tipo "ResponseEntity<Anime>", e, para o parâmetro "id", estamos passando o valor "10".
        log.info(entity);

        //Realizando uma requisição HTTP do tipo "GET" em nossa própria API, com o objetivo de obter um objeto do tipo "Anime".

        Anime object = new RestTemplate().getForObject("http://127.0.0.1:8080/animes/{id}", Anime.class, 10); //Estamos realizando uma requisição HTTP do tipo "GET" na URL "http://localhost:8080/animes/{id}", que retornará um objeto do tipo "Anime", e, para o parâmetro "id", estamos passando o valor "10".
        log.info(object);

        //Realizando uma requisição HTTP do tipo "GET" em nossa própria API, com o objeto de obter um objeto do tipo "ResponseEntity<List<Anime>>".

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://127.0.0.1:8080/animes/list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>(){}); //Através desse método, poderemos receber um objeto do tipo "List<Anime>" em uma URL da nossa própria API.

        log.info(exchange.getBody());

        //Realizando uma requisição HTTP do tipo "POST" em nossa própria API, com o objetivo de adicionar o objeto do tipo "Anime" cujo valor do atributo "nome" é "Kingdom".

        Anime kingdom = Anime.builder().nome("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://127.0.0.1:8080/animes/", kingdom, Anime.class);

        log.info("Anime salvo: " + kingdomSaved); //Estamos enviando uma requisição do tipo "POST" para a nossa API, e estamos passando o objeto "kingdom" para essa requisição, dessa forma, estamos salvando o objeto do tipo "kingdom" em nosso banco de dados.

        //Realizando uma requisição HTTP do tipo "PUT" em nossa própria API, com o objetivo de atualizarmos o valor atributo "nome" do objeto "kingdomSaved" de "Kingdom" para "Kingdom 2" e recebermos um objeto do tipo "ResponseEntity<Void>", que possui o código de retorno dessa requisição HTTP.

        Anime animeToBeUpdated = kingdomSaved;
        animeToBeUpdated.setNome("Kingdom 2");

        ResponseEntity<Void> kingdomUpdated = new RestTemplate().exchange("http://127.0.0.1:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                void.class);

        log.info(kingdomUpdated);

        //Realizando uma requisição HTTP do tipo "DELETE" em nossa própria API, com o objetivo de deletarmos um objeto em nosso banco de dados e exibirmos, no formato de "log", o objeto que foi deletado.

        ResponseEntity<Void> kingdomDeleted = new RestTemplate().exchange("http://127.0.0.1:8080/animes/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                void.class,
                animeToBeUpdated.getId());

        log.info(kingdomDeleted);

    }

    private static HttpHeaders createJsonHeader(){ //Esse método criará o header HTTP de cada método acima que retornará um objeto do tipo "ResponseEntity".
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
