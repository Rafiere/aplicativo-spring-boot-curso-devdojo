package academy.devdojo.springboot2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data //Esse método gera os métodos "getters", "setters", "hashcode()", "equals()" e "toString()" de forma automática ao compilarmos o programa.
@AllArgsConstructor //Gera um método construtor com todos os atributos dessa classe, diminuindo o código "BoilerPlate" da página.
@NoArgsConstructor //Gera um método construtor sem nenhum atributo, diminuindo o código "BoilerPlate" da página.
@Entity //Essa anotação explicita que essa classe será uma tabela do banco de dados.
@Builder //Esse método permite a utilização do método "builder()", que servirá para construirmos um objeto do tipo "Anime" apenas com os atributos desejados.
public class Anime {
    @Id //Essa anotação representa que o atributo "id" será a chave primária da tabela "Anime".
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Essa anotação representa que o valor dos campos da coluna "id" serão geratos automaticamente pelo banco de dados. O valor "GenerationType.IDENTITY" corresponde à estratégia que será utilizada para a geração dessas chaves-primárias.
    private Long id; //Todo objeto do tipo "Anime", obrigatoriamente, deverá ter um valor para os atributos "id" e "nome".

    @NotEmpty(message = "The anime nome cannot be empty")
    private String nome;
}
