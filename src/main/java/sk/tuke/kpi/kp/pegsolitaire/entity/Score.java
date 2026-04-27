package sk.tuke.kpi.kp.pegsolitaire.entity;

import jakarta.persistence.*;
import java.util.Date; 

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Score.getScores",
                query = "SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC"
        ),
        @NamedQuery(
                name = "Score.getTopScores",
                query = "SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC"
        ),
        @NamedQuery(
                name = "Score.resetScores",
                query = "DELETE FROM Score"
        )
})
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_seq")
    @SequenceGenerator(name = "score_seq", sequenceName = "score_seq", allocationSize = 1)
    private Long ident;

    private String player;
    private String game;
    private int points;

    @Temporal(TemporalType.TIMESTAMP)
    private Date playedOn;

    public Score() {
    }

    public Score(String player, String game, int points, Date playedOn) {
        this.player = player;
        this.game = game;
        this.points = points;
        this.playedOn = playedOn;
    }

    public String getPlayer() { return player; }
    public String getGame() { return game; }
    public int getPoints() { return points; }
    public Date getPlayedOn() { return playedOn; }
}
