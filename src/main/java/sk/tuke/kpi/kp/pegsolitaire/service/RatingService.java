package main.java.sk.tuke.kpi.kp.pegsolitaire.service;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getAverageRating(String game);
    int getRating(String game, String player);
}