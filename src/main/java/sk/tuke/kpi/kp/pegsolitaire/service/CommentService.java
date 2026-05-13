package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getComments(String game);
    void reset();
    Comment getCommentByPlayer(String game, String player);
    void updateComment(Comment comment);
    void deleteComment(String game, String player);

}
