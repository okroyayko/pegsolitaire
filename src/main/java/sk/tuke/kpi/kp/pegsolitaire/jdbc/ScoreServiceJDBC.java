package main.java.sk.tuke.kpi.kp.pegsolitaire.jdbc;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ScoreServiceJDBC {
    private final String url;
    private final String user;
    private final String pass;

    public ScoreServiceJDBC() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/db.properties")) {
            Properties p = new Properties();
            p.load(in);
            url = p.getProperty("db.url");
            user = p.getProperty("db.user");
            pass = p.getProperty("db.password");
        }
    }

    public void addScore(String game, String player, int points) {
        String sql = "INSERT INTO score (game, player, points, played_at) VALUES (?, ?, ?, now())";
        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, game);
            ps.setString(2, player);
            ps.setInt(3, points);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}