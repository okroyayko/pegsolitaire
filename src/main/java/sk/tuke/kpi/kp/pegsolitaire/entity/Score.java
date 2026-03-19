package main.java.sk.tuke.kpi.kp.pegsolitaire.entity;

import java.util.Date;

public class Score {
    private String player;
    private String game;
    private int points;
    private Date playedOn;

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