package sk.tuke.kpi.kp.pegsolitaire;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PegSolitaireApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}

