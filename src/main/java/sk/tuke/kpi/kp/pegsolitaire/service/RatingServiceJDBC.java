package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import java.sql.*;

public class RatingServiceJDBC implements RatingService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "DB123456db";

    @Override
    public void setRating(Rating rating) {
        String sql = """
            INSERT INTO rating (player, game, rating)
            VALUES (?, ?, ?)
            ON CONFLICT (player, game)
            DO UPDATE SET rating = EXCLUDED.rating
        """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, rating.getPlayer());
            ps.setString(2, rating.getGame());
            ps.setInt(3, rating.getRating());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRating(String player, String game) {
        String sql = "SELECT rating FROM rating WHERE player = ? AND game = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, player);
            ps.setString(2, game);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("rating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getAverageRating(String game) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM rating WHERE game = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, game);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("avg_rating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
