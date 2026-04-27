package sk.tuke.kpi.kp.pegsolitaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-web.properties")
public class PegSolitaireApplication {
    public static void main(String[] args) {
        SpringApplication.run(PegSolitaireApplication.class, args);
    }
}
