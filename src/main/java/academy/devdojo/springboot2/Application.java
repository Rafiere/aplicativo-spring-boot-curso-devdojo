package academy.devdojo.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Basicamente, essa anotação serve para configurar a classe que inicializará o projeto, que é essa própria classe. Dentro dessa anotação, existem várias outras anotações que configuram componentes do sistema.
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
