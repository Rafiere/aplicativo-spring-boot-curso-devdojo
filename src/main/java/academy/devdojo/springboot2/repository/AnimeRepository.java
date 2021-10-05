package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository <Anime, Long> { //A interface "JpaRepository" faz parte do "Spring Data JPA", dessa forma, ao implementarmos essa interface, vários métodos relacionados à realização de operações simples nessa tabela serão implementados automaticamente. Dentro do "diamond operator" dessa interface, deveremos, primeiramente, passar a classe na qual essa interface estará representando, e, no segundo argumento, deveremos passar o tipo da chave primária, assim, como a chave primária está representada pelo atributo "id" da classe "Anime", que é do tipo "Long", deveremos passar essa classe como o segundo argumento do "diamond operator".

}
