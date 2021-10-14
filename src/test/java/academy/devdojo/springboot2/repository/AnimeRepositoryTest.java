package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.AnimeCreator;
import com.mysql.cj.log.Log;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest //Essa anotação é utilizada para explicitarmos que essa classe servirá apenas para a realização de testes em componentes do JPA, assim, essa anotação configurará, de forma automática, um banco de dados embarcado para realizar os testes, incluindo outras configurações que devem ser inseridas para realizarmos testes nos componentes JPA da aplicação.
@DisplayName("Tests for AnimeRepository") //Nome dessa classe de testes.
class AnimeRepositoryTest {

    @Autowired //Não é recomendada a injeção de dependência através da anotação "@Autowired", porém, como essa classe é utilizada apenas para a realização de testes, podemos realizar a injeção de dependência através da anotação "@Autowired" sem nenhum problema.
    private AnimeRepository animeRepository;

    @Test //Essa anotação indica que esse método será um teste do JUnit.
    @DisplayName("Save persists anime when successful.") //Essa anotação permite a inserção de um nome para esse teste, dessa forma, após executarmos esse teste, o valor que inserimos nessa anotação será considerado como o nome do teste.
    void save_PersistAnime_WhenSuccessful(){ //Existem vários padrões para a nomeação de testes, porém, de acordo com o padrão utilizado pelo professor, os nomes dos testes deverão seguir o formato "nomeDoMetodoQueEstaSendoTestado_TarefaQueOMetodoDeveExecutar_QuandoOMetodoDeveExecutar", assim, nesse exemplo, estamos testando o método "save()", esse método deverá persistir um objeto do tipo "Anime" quando o teste for realizado com sucesso.
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //O objeto "animeToBeSaved" receberá o retorno do método "AnimeCreator.createAnimeToBeSaved()".
        Anime animeSaved = this.animeRepository.save(animeToBeSaved); //O objeto "animeSaved" receberá o retorno do método "save()", que é o método que está sendo testado.
        Assertions.assertThat(animeSaved).isNotNull(); //Se o valor do objeto "animeSaved" for "null", o teste retornará uma falha.
        Assertions.assertThat(animeSaved.getId()).isNotNull(); //Se o valor do atributo "id" do objeto "animeSaved" for "null", o teste retornará uma falha.
        Assertions.assertThat(animeSaved.getNome()).isEqualTo(animeToBeSaved.getNome()); //Se o valor do atributo "nome" do objeto "animeSaved" não for igual ao valor do atributo "nome" do objeto "animeToBeSaved", o teste retornará uma falha.
        //Se nenhuma verificação acima retornar "true", o teste será executado com sucesso.
    }

    @Test //Essa anotação indica que esse método será um teste do JUnit.
    @DisplayName("Save updates anime when successful.") //Essa anotação permite a inserção de um nome para esse teste, dessa forma, após executarmos esse teste, o valor que inserimos nessa anotação será considerado como o nome do teste.
    void save_UpdatesAnime_WhenSuccessful(){ //Existem vários padrões para a nomeação de testes, porém, de acordo com o padrão utilizado pelo professor, os nomes dos testes deverão seguir o formato "nomeDoMetodoQueEstaSendoTestado_TarefaQueOMetodoDeveExecutar_QuandoOMetodoDeveExecutar", assim, nesse exemplo, estamos testando o método "save()", esse método deverá persistir um objeto do tipo "Anime" quando o teste for realizado com sucesso.
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //O objeto "animeToBeSaved" receberá o retorno do método "AnimeCreator.createAnimeToBeSaved()".
        Anime animeSaved = this.animeRepository.save(animeToBeSaved); //O objeto "animeSaved" receberá o retorno do método "save()", que é o método que está sendo testado.
        animeSaved.setNome("Overlord");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        Assertions.assertThat(animeUpdated).isNotNull(); //Se o valor do objeto "animeSaved" for "null", o teste retornará uma falha.
        Assertions.assertThat(animeUpdated.getId()).isNotNull(); //Se o valor do atributo "id" do objeto "animeSaved" for "null", o teste retornará uma falha.
        Assertions.assertThat(animeUpdated.getNome()).isEqualTo(animeToBeSaved.getNome()); //Se o valor do atributo "nome" do objeto "animeSaved" não for igual ao valor do atributo "nome" do objeto "animeToBeSaved", o teste retornará uma falha.
        //Se nenhuma verificação acima retornar "true", o teste será executado com sucesso.
    }

    @Test
    @DisplayName("Delete removes anime when successful.")
    void delete_RemovesAnime_WhenSuccessful(){ //Esse método testará se, ao utilizarmos o método "delete()" em um objeto do tipo "Anime", se esse objeto será realmente removido do banco de dados.
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //Estamos criando um objeto do tipo "Anime".
        Anime animeSaved = this.animeRepository.save(animeToBeSaved); //Estamos salvando o objeto do tipo "Anime" que foi criado no banco de dados, e, após isso, estamos armazenando o objeto do tipo "Anime", que foi salvo, na variável "animeSaved".
        this.animeRepository.delete(animeSaved); //Estamos utilizando o método "delete()" no objeto do tipo "Anime" que foi salvo.
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId()); //Estamos selecionando, através do método "findById()", o objeto do tipo "Anime" que foi salvo, de acordo com o valor do atributo "nome" desse objeto. Estamos armazenando o retorno do método "findById()" em um objeto do tipo "Optional<Anime>", pois existe a possibilidade desse método retornar "null", assim, ao utilizarmos um objeto do tipo "Optional<Anime>", evitaremos o surgimento da exception "NullPointerException".
        Assertions.assertThat(animeOptional).isEmpty(); //Estamos verificando se o objeto "animeOptional" está vazio, pois, se esse teste falhar, isso significa que o objeto não foi excluído corretamente.
    }

    @Test
    @DisplayName("Find By Nome returns list of anime when successful.") //Estamos testando o método "findByNome()" e verificando se, ao salvarmos um objeto do tipo "Anime" através do método "save()", que está sendo testado acima, esse objeto será realmente salvo no objeto "animes", que é do tipo "List<Anime>".
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //Estamos criando um objeto do tipo "Anime" e salvando esse objeto no objeto "animeToBeSaved()".
        Anime animeSaved = this.animeRepository.save(animeToBeSaved); //Estamos salvando esse objeto no banco de dados e salvando o retorno do método "save()", que é o objeto que foi salvo, no objeto "animeSaved".
        String nome = animeSaved.getNome(); //Estamos obtendo o valor do atributo "nome" do objeto que foi salvo.
        List<Anime> animes = this.animeRepository.findByNome(nome); //Estamos verificando se existe um objeto, dentro do objeto "animes", cujo valor do atributo "nome" seja o valor que está dentro da variável "nome".
        Assertions.assertThat(animes).isNotEmpty(); //Estamos verificando se a lista "animes" não está vazia.
        Assertions.assertThat(animes).contains(animeSaved); //Estamos verificando se a lista "animes" contém o objeto que está dentro da variável "animeSaved", ou seja, o objeto que foi salvo.
    }

    @Test
    @DisplayName("Find By Nome returns empty list when no anime is found.") //Estamos testando se o método "findByNome()" retorna uma lista vazia se passarmos, como parâmetro para esse método, um valor que não está inserido em nenhum atributo "nome" dos objetos do tipo "Anime" que estão inseridos na lista "animes".
    void findByNome_ReturnsEmptyList_WhenAnimeIsNotFound(){

        List<Anime> animes = this.animeRepository.findByNome("Testando"); //Estamos passando para o método "findByNome()" um valor que não corresponde a nenhum valor do atributo "nome" dos objetos do tipo "Anime" que estão na lista "animes".

        Assertions.assertThat(animes).isEmpty(); //Estamos verificando se a lista continua vazia, que seria o comportamento correto.
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when nome is empty") //Esse teste verificará se a exception lançada ao enviarmos, para o método "save()", um objeto do tipo "Anime" cujo valor do atributo "nome" seja
    void save_ThrowsConstraintViolationException_WhenNomeIsEmpty(){
        Anime anime = new Anime(); //Estamos criando o objeto "anime", que é do tipo "Anime".
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .hasMessageContaining("The anime nome cannot be empty"); //Estamos verificando se a exception que será lançada ao executarmos o método "save()" possui a mensagem "The anime nome cannot be empty".
    }
}