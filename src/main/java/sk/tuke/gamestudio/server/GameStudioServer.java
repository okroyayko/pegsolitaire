package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreServiceJPA;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentServiceJPA;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingServiceJPA;

@SpringBootApplication
@EntityScan("sk.tuke.kpi.kp.pegsolitaire.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
