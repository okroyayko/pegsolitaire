package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getAverageRating(String game);
    int getRating(String player, String game);
}