package main.java.sk.tuke.kpi.kp.pegsolitaire.service;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "app_user";
    private static final String PASSWORD = "StrongPass123!";

    @Override
    public void setRating(Rating rating) {
        String delete = "DELETE FROM rating WHERE game = ? AND player = ?";
        String insert = "INSERT INTO rating (player, game, rating) VALUES (?, ?, ?)";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD)) {

            try (PreparedStatement ps = c.prepareStatement(delete)) {
                ps.setString(1, rating.getGame());
                ps.setString(2, rating.getPlayer());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(insert)) {
                ps.setString(1, rating.getPlayer());
                ps.setString(2, rating.getGame());
                ps.setInt(3, rating.getRating());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAverageRating(String game) {
        String sql = "SELECT AVG(rating) FROM rating WHERE game = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, game);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getRating(String game, String player) {
        String sql = "SELECT rating FROM rating WHERE game = ? AND player = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, game);
            ps.setString(2, player);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}