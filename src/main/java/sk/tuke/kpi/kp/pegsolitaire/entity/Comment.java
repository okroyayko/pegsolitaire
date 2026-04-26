package sk.tuke.kpi.kp.pegsolitaire.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Comment.getComments",
                query = "SELECT c FROM Comment c WHERE c.game = :game ORDER BY c.commentedOn DESC"
        ),
        @NamedQuery(
                name = "Comment.resetComments",
                query = "DELETE FROM Comment"
        )
})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    private Long ident;

    private String game;
    private String player;
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentedOn;

    public Comment() {
    }

    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public String getGame() { return game; }
    public String getPlayer() { return player; }
    public String getComment() { return comment; }
    public Date getCommentedOn() { return commentedOn; }
}
