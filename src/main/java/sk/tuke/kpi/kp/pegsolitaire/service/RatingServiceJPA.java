package sk.tuke.kpi.kp.pegsolitaire.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        entityManager
                .createNamedQuery("Rating.resetRatings")
                .executeUpdate();
        entityManager.persist(rating);
    }

    @Override
    public int getRating(String game, String player) {
        List<Rating> list = entityManager
                .createNamedQuery("Rating.getRating", Rating.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getResultList();

        return list.isEmpty() ? 0 : list.get(0).getRating();
    }

    @Override
    public int getAverageRating(String game) {
        Double avg = entityManager
                .createNamedQuery("Rating.getAverageRating", Double.class)
                .setParameter("game", game)
                .getSingleResult();

        return avg == null ? 0 : avg.intValue();
    }

    @Override
    public void reset() {
        entityManager
                .createNamedQuery("Rating.resetRatings")
                .executeUpdate();
    }
}
