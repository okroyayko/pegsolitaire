package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getRating(String game, String player);
    int getAverageRating(String game);
    void reset();
}
