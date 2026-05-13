package sk.tuke.kpi.kp.pegsolitaire.service;

import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "DB123456db";

    @Override
    public void addComment(Comment comment) {
        String sql = "INSERT INTO comment (player, game, comment, commented_on) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, comment.getPlayer());
            ps.setString(2, comment.getGame());
            ps.setString(3, comment.getComment());
            ps.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        String sql = "SELECT player, game, comment, commented_on FROM comment WHERE game = ? ORDER BY commented_on DESC";
        List<Comment> comments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, game);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getString("player"),
                        rs.getString("game"),
                        rs.getString("comment"),
                        rs.getTimestamp("commented_on")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    @Override
    public Comment getCommentByPlayer(String game, String player) {
        // JDBC version not used anymore
        return null;
    }

    @Override
    public void updateComment(Comment comment) {
        // JDBC version not used anymore
    }

    @Override
    public void deleteComment(String game, String player) {
        // JDBC version not used anymore
    }


    @Override
    public void reset() {
        String sql = "DELETE FROM comment";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
