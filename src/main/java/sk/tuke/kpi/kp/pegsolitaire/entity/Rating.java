package main.java.sk.tuke.kpi.kp.pegsolitaire.entity;

public class Rating {
    private String player;
    private String game;
    private int rating;

    public Rating(String player, String game, int rating) {
        this.player = player;
        this.game = game;
        this.rating = rating;
    }

    public String getPlayer() { return player; }
    public String getGame() { return game; }
    public int getRating() { return rating; }
}