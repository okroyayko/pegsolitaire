package sk.tuke.kpi.kp.pegsolitaire.test;

import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreServiceJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceJDBCTest {

    private ScoreService service;

    @BeforeEach
    public void setup() throws Exception {
        service = new ScoreServiceJDBC();

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gamestudio",
                "postgres",
                "DB123456db"
        );
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM score");
        conn.close();
    }

    @Test
    public void testAddAndGetScores() {
        Score s1 = new Score("Zoryan", "pegsolitaire", 95, new Date());
        Score s2 = new Score("Ivan", "pegsolitaire", 88, new Date());

        service.addScore(s1);
        service.addScore(s2);

        List<Score> scores = service.getTopScores("pegsolitaire");

        assertEquals(2, scores.size());
        assertEquals("Zoryan", scores.get(0).getPlayer());
        assertEquals(95, scores.get(0).getPoints());
    }
}