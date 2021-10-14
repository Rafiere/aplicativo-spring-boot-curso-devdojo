package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //Ao utilizarmos essa anotação, estamos declarando que queremos utilizar o JUnit com o Spring, e não queremos subir a aplicação inteira para realizarmos os testes, como ocorreria se utilizássemos a anotação "@SpringBootTest".
class AnimeControllerTest {

    @InjectMocks //Essa anotação deverá ser utilizada na classe que queremos testar, assim, como essa classe testará a classe "AnimeController", deveremos inserir essa classe sob a declaração de um objeto do tipo "AnimeController".
    private AnimeController animeController;

    @Mock //Essa anotação deverá ser inserida sob todos os objetos cuja classe esteja sendo utilizada na classe que está sendo testada, que é a classe "AnimeController".
    private AnimeService animeServiceMock; //Esse objeto simulará todos os comportamentos da classe "AnimeService".

    @BeforeEach //Antes de cada um dos testes serem executados, o código abaixo será executado.
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())) //Quando algum elemento dessa classe utilizar o método "listAll()" do objeto "animeServiceMock", independentemente dos argumentos que forem passados para esse método, esse método retornará um objeto do tipo "AnimePage". Esse objeto do tipo "AnimePage" é uma lista, do tipo "Page<Anime>", que contém apenas um objeto do tipo "Anime". Esse método serve para simularmos o método "listAll()", da classe "AnimeService", que retorna um objeto do tipo "Page<Anime>", que está sendo chamado no método "public ResponseEntity<Page<Anime>> list(Pageable pageable)" da classe "AnimeController".
                .thenReturn(animePage); //Sempre que um método dessa classe chamar o método "listAll()", independentemente dos argumentos que forem passados, o método que será executado não será o método "listAll()" que está sendo implementado na classe "AnimeService", e sim, independente do que aconteça dentro do método "listAll()", o valor retornado por esse método será um objeto do tipo "PageImpl<Anime>', que foi inserido acima. Isso apenas acontecerá por causa da anotação "@BeforeEach".

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByNome(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){ //Esse método está testando o método "list()" da classe "AnimeController", dessa forma, para esse teste ser bem sucedido, o método "list()" deverá retornar um objeto do tipo "Page<Anime>" que passe por todas as verificações. Como estamos criando um "mock" da classe "AnimeController" e estamos, dentro do método "setUp()", inserindo, através do objeto "BDDMockito", uma condição que, ao executarmos o método "listAll()" dentro dessa classe, independentemente dos argumentos que forem passados, será retornado um objeto do tipo "Page<Anime>", esse teste deve ser executado com sucesso.
        String expectedName = AnimeCreator.createValidAnime().getNome();
        Page<Anime> animePage = animeController.list(null).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getNome()).isEqualTo(expectedName);
    }

}