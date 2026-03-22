package main.java.sk.tuke.kpi.kp.pegsolitaire.test;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.CommentServiceJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceJDBCTest {

    private CommentService service;

    @BeforeEach
    public void setup() throws Exception {
        service = new CommentServiceJDBC();

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gamestudio",
                "postgres",
                "DB123456db"
        );
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM comment");
        conn.close();
    }

    @Test
    public void testAddAndGetComments() {
        Comment c1 = new Comment("Zoryan", "pegsolitaire", "Cool game!", new Date());
        Comment c2 = new Comment("Ivan", "pegsolitaire", "Hard but fun", new Date());

        service.addComment(c1);
        service.addComment(c2);

        List<Comment> comments = service.getComments("pegsolitaire");

        assertEquals(2, comments.size());
        assertEquals("Zoryan", comments.get(0).getPlayer());
        assertEquals("Cool game!", comments.get(0).getComment());
    }
}