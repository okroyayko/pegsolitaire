package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "DB123456db";

    @Override
    public void addScore(Score score) {
        String sql = "INSERT INTO score (player, game, points, played_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, score.getPlayer());
            ps.setString(2, score.getGame());
            ps.setInt(3, score.getPoints());
            ps.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding score", e);
        }
    }

    @Override
    public List<Score> getScores(String game) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT player, game, points, played_at FROM score WHERE game = ? ORDER BY points DESC";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, game);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Score(
                        rs.getString("player"),
                        rs.getString("game"),
                        rs.getInt("points"),
                        rs.getTimestamp("played_at")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error loading scores", e);
        }

        return list;
    }

    @Override
    public List<Score> getTopScores(String game) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT player, game, points, played_at FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, game);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Score(
                        rs.getString("player"),
                        rs.getString("game"),
                        rs.getInt("points"),
                        rs.getTimestamp("played_at")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error loading top scores", e);
        }

        return list;
    }

    @Override
    public void reset() {
        String sql = "DELETE FROM score";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Error resetting scores", e);
        }
    }
}
