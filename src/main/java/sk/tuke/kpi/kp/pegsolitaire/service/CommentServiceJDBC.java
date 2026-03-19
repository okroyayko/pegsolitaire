package main.java.sk.tuke.kpi.kp.pegsolitaire.service;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {

    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "app_user";
    private static final String PASSWORD = "StrongPass123!";

    @Override
    public void addComment(Comment comment) {
        String sql = "INSERT INTO comment (player, game, comment, commented_on) VALUES (?, ?, ?, ?)";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

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
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT player, game, comment, commented_on FROM comment WHERE game = ? ORDER BY commented_on DESC";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, game);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Comment(
                            rs.getString("player"),
                            rs.getString("game"),
                            rs.getString("comment"),
                            rs.getTimestamp("commented_on")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}