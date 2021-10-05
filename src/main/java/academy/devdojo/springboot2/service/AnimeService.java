package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service //Anotação semântica que indica que essa classe é um "bean" do Spring.
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository; //Estamos injetando a interface que contém vários métodos de manipulação do banco de dados.

    public List<Anime> listAll(){ //Essa classe está implementando os métodos que serão chamados pela classe "AnimeController".
        return animeRepository.findAll(); //Estamos utilizando o método "findAll()" da interface "animeRepository".
    }

    public Anime findByIdOrThrowBadRequestException(Long id){ //Método que receberá um valor do tipo "Long" e procurará no objeto "animes", que é do tipo "List<Anime>", o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor passado como argumento para esse método.
        return animeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime ID not found.")); //Se não encontrarmos o ID desejado, retornaremos o status HTTP "BAD_REQUEST").
    }

    public Anime save(AnimePostRequestBody animePostRequestBody){ //Método que receberá um objeto da classe "AnimePostRequestBody", que contém os mesmos atributos de um objeto da classe "Anime", e construirá um objeto do tipo "Anime" apenas com o valor do atributo "nome" desse objeto do tipo "AnimePostRequestBody", dessa forma, o valor do atributo "id" será gerado automaticamente pelo banco de dados, pois inserimos a anotação "@GeneratedValue(strategy = GenerationType.IDENTITY)" no atributo "id'.
        Anime anime = Anime.builder().nome(animePostRequestBody.getNome()).build(); //Estamos
        return animeRepository.save(anime);
    }

    public void delete(Long id){ //Método que deletará o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor do parâmetro "id" passado para esse método.
        animeRepository.delete(findByIdOrThrowBadRequestException(id)); //Utilizando o método "delete()" da interface "AnimeRepository", que foi herdado da interface "JpaRepository".
    }

    public void replace(AnimePutRequestBody animePutRequestBody){ //Método que receberá um objeto do tipo "Anime", deletará, se existir, o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor do atributo "id" do objeto do tipo "Anime" que foi passado para esse método e criará um novo objeto do tipo "Anime", cujo valor do atributo "nome" será o valor do atributo "nome" do objeto do tipo "Anime" que foi passado para esse método.
        Anime anime = Anime.builder() //Estamos construindo um objeto do tipo "Anime" que possuirá o valor dos atributos "nome" e "id".
                        .id(animePutRequestBody.getId())
                        .nome(animePutRequestBody.getNome())
                        .build();
        animeRepository.save(anime); //Salvaremos no banco de dado o novo objeto que foi recebido.
    }
}
