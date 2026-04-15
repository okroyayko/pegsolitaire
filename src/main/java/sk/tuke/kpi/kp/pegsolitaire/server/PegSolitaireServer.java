package sk.tuke.kpi.kp.pegsolitaire.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "sk.tuke.kpi.kp.pegsolitaire")
@EntityScan("sk.tuke.kpi.kp.pegsolitaire.entity")
@EnableJpaRepositories("sk.tuke.kpi.kp.pegsolitaire.service")
public class PegSolitaireServer {
    public static void main(String[] args) {
        SpringApplication.run(PegSolitaireServer.class, args);
    }
}

