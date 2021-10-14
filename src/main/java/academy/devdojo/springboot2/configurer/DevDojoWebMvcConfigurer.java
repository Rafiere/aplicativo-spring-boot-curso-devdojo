package academy.devdojo.springboot2.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration //Essa anotação explicita que essa é uma classe de configuração do Spring.
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(0, 5)); //Estamos definindo que, se não passarmos nenhum parâmetro na URL que está chamando um método que retorna um objeto do tipo "Page", a página padrão que esse objeto irá retornar é a página "0" e que cada página terá apenas 5 elementos.
        resolvers.add(pageHandler);
    }
}
