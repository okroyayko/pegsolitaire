package sk.tuke.kpi.kp.pegsolitaire.service.rest;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService {

    private static final String URL = "http://localhost:8080/api/score";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addScore(Score score) {
        try {
            restTemplate.postForEntity(URL, score, Score.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error adding score via REST", e);
        }
    }

    @Override
    public List<Score> getScores(String game) {
        try {
            Score[] scores = restTemplate.getForObject(URL + "/" + game, Score[].class);
            return scores == null ? List.of() : Arrays.asList(scores);
        } catch (RestClientException e) {
            throw new RuntimeException("Error loading scores via REST", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try {
            Score[] scores = restTemplate.getForObject(URL + "/" + game + "/top", Score[].class);
            return scores == null ? List.of() : Arrays.asList(scores);
        } catch (RestClientException e) {
            throw new RuntimeException("Error loading top scores via REST", e);
        }
    }

    @Override
    public void reset() {
        try {
            restTemplate.delete(URL);
        } catch (RestClientException e) {
            throw new RuntimeException("Error resetting scores via REST", e);
        }
    }
}
