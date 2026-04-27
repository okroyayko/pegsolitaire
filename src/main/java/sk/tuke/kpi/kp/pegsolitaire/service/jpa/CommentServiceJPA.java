package sk.tuke.kpi.kp.pegsolitaire.service.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;

import java.util.List;

@Service
@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager
                .createNamedQuery("Comment.getComments", Comment.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager
                .createNamedQuery("Comment.resetComments")
                .executeUpdate();
    }
}
