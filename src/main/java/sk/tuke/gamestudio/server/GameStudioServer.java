package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import sk.tuke.kpi.kp.pegsolitaire.service.rest.CommentServiceRestClient;
import sk.tuke.kpi.kp.pegsolitaire.service.rest.RatingServiceRestClient;
import sk.tuke.kpi.kp.pegsolitaire.service.rest.ScoreServiceRestClient;

@SpringBootApplication
@EntityScan("sk.tuke.kpi.kp.pegsolitaire.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

}
