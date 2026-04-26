package sk.tuke.kpi.kp.pegsolitaire.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;

import java.util.List;

@Service
@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getScores(String game) {
        return entityManager
                .createNamedQuery("Score.getScores", Score.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public List<Score> getTopScores(String game) {
        return entityManager
                .createNamedQuery("Score.getTopScores", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager
                .createNamedQuery("Score.resetScores")
                .executeUpdate();
    }
}
