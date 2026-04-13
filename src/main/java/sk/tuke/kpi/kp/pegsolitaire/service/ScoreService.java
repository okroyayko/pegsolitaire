package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<Score> getTopScores(String game);
    void reset();
}
