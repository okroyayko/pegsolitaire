package sk.tuke.kpi.kp.pegsolitaire.service.rest;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService {

    private static final String URL = "http://localhost:8080/api/score";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(URL, score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        Score[] scores = restTemplate.getForObject(URL + "/" + game, Score[].class);
        return Arrays.asList(scores);
    }

    @Override
    public void reset() {
        restTemplate.delete(URL);
    }
}
