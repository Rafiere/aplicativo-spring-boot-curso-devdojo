package academy.devdojo.springboot2.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component //Essa anotação explicita que essa classe poderá ser gerenciada pelo Spring, possibilitando a injeção de dependências. Eu não entendi muito bem esse conceito.
public class DateUtil {
    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime){ //Esse método receberá um objeto do tipo "LocalDateTime" e converterá essa data para o formato que será utilizado no banco de dados.
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
