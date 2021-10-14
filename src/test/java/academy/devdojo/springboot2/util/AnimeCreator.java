package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator { //Dentro dessa classe, criaremos todos objetos que utilizaremos nas classes de teste. Esses objetos servirão para simular a obtenção de objetos do banco de dados, pois ele não será utilizado nos testes.

    public static Anime createAnimeToBeSaved(){ //Método que retornará um objeto do tipo "Anime" que não possui nenhum valor no atributo "id".
        return Anime.builder()
                .nome("Hajime no Ippo")
                .build();
    }

    public static Anime createValidAnime(){ //Método que retornará um objeto do tipo "Anime" que possuirá um valor nos atributos "id" e "nome".
        return Anime.builder()
                .nome("Hajime no Ippo")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime(){ //Método que retornará um objeto do tipo "Anime" cujo valor do atributo "id" será igual ao valor do atributo "id" do objeto retornado pelo método "createValidAnime", porém, o valor do atributo "nome" será diferente.
        return Anime.builder()
                .nome("Hajime no Ippo")
                .id(1L)
                .build();
    }

}
