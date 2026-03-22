package main.java.sk.tuke.kpi.kp.pegsolitaire.test;

import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.RatingServiceJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
        service.setRating(new Rating("Zoryan", "pegsolitaire", 5));

        int rating = service.getRating("Zoryan", "pegsolitaire");

        assertEquals(5, rating);
    }

    @Test
    public void testAverageRating() {
        service.setRating(new Rating("Zoryan", "pegsolitaire", 5));
        service.setRating(new Rating("Ivan", "pegsolitaire", 3));

        int avg = service.getAverageRating("pegsolitaire");

        assertEquals(4, avg);
    }
}