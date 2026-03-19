package main.java.sk.tuke.kpi.kp.pegsolitaire.entity;

import java.util.Date;

public class Comment {
    private String player;
    private String game;
    private String comment;
    private Date commentedOn;

    public Comment(String player, String game, String comment, Date commentedOn) {
        this.player = player;
        this.game = game;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public String getComment() {
        return comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }
}