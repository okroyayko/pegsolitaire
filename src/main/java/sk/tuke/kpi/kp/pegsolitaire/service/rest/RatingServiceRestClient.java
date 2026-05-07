package sk.tuke.kpi.kp.pegsolitaire.service.rest;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;

public class RatingServiceRestClient implements RatingService {

    private static final String URL = "http://localhost:8084/api/rating";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(URL, rating, Rating.class);
    }

    @Override
    public int getRating(String game, String player) {
        return restTemplate.getForObject(URL + "/" + game + "/" + player, Integer.class);
    }

    @Override
    public int getAverageRating(String game) {
        return restTemplate.getForObject(URL + "/" + game + "/average", Integer.class);
    }

    @Override
    public void reset() {
        restTemplate.delete(URL);
    }
}
