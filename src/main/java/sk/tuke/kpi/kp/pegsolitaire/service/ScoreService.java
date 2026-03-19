package main.java.sk.tuke.kpi.kp.pegsolitaire.service;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<Score> getBestScores(String game);
}