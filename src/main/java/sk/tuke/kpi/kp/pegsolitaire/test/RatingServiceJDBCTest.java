package sk.tuke.kpi.kp.pegsolitaire.test;

import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingServiceJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceJDBCTest {

    private RatingService service;

    @BeforeEach
    public void setup() throws Exception {
        service = new RatingServiceJDBC();

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gamestudio",
                "postgres",
                "DB123456db"
        );
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM rating");
        conn.close();
    }

    @Test
    public void testSetAndGetRating() {
        service.setRating(new Rating("Zoryan", "pegsolitaire", 5, new Date()));

        int rating = service.getRating("pegsolitaire", "Zoryan");

        assertEquals(5, rating);
    }

    @Test
    public void testAverageRating() {
        service.setRating(new Rating("Zoryan", "pegsolitaire", 5, new Date()));
        service.setRating(new Rating("Ivan", "pegsolitaire", 3, new Date()));

        int avg = service.getAverageRating("pegsolitaire");

        assertEquals(4, avg);
    }
}
