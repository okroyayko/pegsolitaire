package main.java.sk.tuke.kpi.kp.pegsolitaire.service;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "app_user";
    private static final String PASSWORD = "StrongPass123!";

    @Override
    public void addScore(Score score) {
        String sql = "INSERT INTO score (player, game, points, played_on) VALUES (?, ?, ?, ?)";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, score.getPlayer());
            ps.setString(2, score.getGame());
            ps.setInt(3, score.getPoints());
            ps.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> getBestScores(String game) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT player, game, points, played_on FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, game);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Score(
                            rs.getString("player"),
                            rs.getString("game"),
                            rs.getInt("points"),
                            rs.getTimestamp("played_on")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}