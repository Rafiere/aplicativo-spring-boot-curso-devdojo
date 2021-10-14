package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service //Anotação semântica que indica que essa classe é um "bean" do Spring.
@RequiredArgsConstructor //Anotação utilizada para realizarmos a injeção de dependência através do método construtor.
public class AnimeService {

    private final AnimeRepository animeRepository; //Estamos injetando a interface que contém vários métodos de manipulação do banco de dados.

    public List<Anime> listAll(){ //Esse método não utilizará a paginação, ele foi criado para evitar a criação de uma classe DTO apenas para obter os atributos do objeto mapeado.
        return animeRepository.findAll();
    }

    public Page<Anime> listAll(Pageable pageable){ //Ao retornarmos um objeto do tipo "Page<Anime>" e recebermos um objeto do tipo "Pageable", estamos utilizando o recurso de "paginação" nativo do Spring.
        return animeRepository.findAll(pageable); //Estamos utilizando o método "findAll()" da interface "animeRepository" para listarmos todos os objetos do tipo "Anime" utilizando o recurso de paginação do Spring.
    }

    public Anime findByIdOrThrowBadRequestException(Long id){ //Método que receberá um valor do tipo "Long" e procurará no objeto "animes", que é do tipo "List<Anime>", o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor passado como argumento para esse método.
        return animeRepository.findById(id).orElseThrow(() -> new BadRequestException("Anime ID not found.")); //Se não encontrarmos o ID desejado, retornaremos a exception personalizada "BadRequestException", que corresponde ao status HTTP "BAD_REQUEST").
    }

    public List<Anime> findByNome(String nome){ //Método que receberá o objeto "nome", que será do tipo "String" e chamará o método "findByNome()" da interface "AnimeRepository", passando a String "nome", que foi recebida, para esse método.
        return animeRepository.findByNome(nome);
    }

    @Transactional //Essa anotação explicita que, caso qualquer exception do tipo "unchecked exception" ocorra dentro desse método, todas as alterações realizadas no banco de dados por esse método deverão ser desfeitas, ou seja, essas alterações não serão commitadas.
    public Anime save(AnimePostRequestBody animePostRequestBody){ //Método que receberá um objeto da classe "AnimePostRequestBody", que contém os mesmos atributos de um objeto da classe "Anime", e construirá um objeto do tipo "Anime" apenas com o valor do atributo "nome" desse objeto do tipo "AnimePostRequestBody", dessa forma, o valor do atributo "id" será gerado automaticamente pelo banco de dados, pois inserimos a anotação "@GeneratedValue(strategy = GenerationType.IDENTITY)" no atributo "id'.
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        return animeRepository.save(anime);
    }

    public void delete(Long id){ //Método que deletará o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor do parâmetro "id" passado para esse método.
        animeRepository.delete(findByIdOrThrowBadRequestException(id)); //Utilizando o método "delete()" da interface "AnimeRepository", que foi herdado da interface "JpaRepository".
    }

    public void replace(AnimePutRequestBody animePutRequestBody){ //Método que receberá um objeto do tipo "Anime", deletará, se existir, o objeto do tipo "Anime" cujo valor do atributo "id" for igual ao valor do atributo "id" do objeto do tipo "Anime" que foi passado para esse método e criará um novo objeto do tipo "Anime", cujo valor do atributo "nome" será o valor do atributo "nome" do objeto do tipo "Anime" que foi passado para esse método.
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(animePutRequestBody.getId());
        animeRepository.save(anime); //Salvaremos no banco de dado o novo objeto que foi recebido.
    }
}
