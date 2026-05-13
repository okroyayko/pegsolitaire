package sk.tuke.kpi.kp.pegsolitaire.service.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;

import java.util.List;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {

        // шукаємо існуючий рейтинг цього гравця для цієї гри
        List<Rating> list = entityManager
                .createNamedQuery("Rating.getRating", Rating.class)
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .getResultList();

        if (list.isEmpty()) {
            // якщо ще не ставив — створюємо новий запис
            entityManager.persist(rating);
        } else {
            // якщо вже ставив — оновлюємо значення
            Rating existing = list.get(0);
            existing.setRating(rating.getRating());
            existing.setRatedOn(rating.getRatedOn());
            entityManager.merge(existing);
        }
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
