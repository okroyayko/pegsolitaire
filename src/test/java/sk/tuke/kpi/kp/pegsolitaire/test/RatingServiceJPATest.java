package sk.tuke.kpi.kp.pegsolitaire.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class RatingServiceJPATest {

    @Autowired
    private RatingService service;

    @Test
    public void testSetAndGetRating() {
        service.reset();

        service.setRating(new Rating("pegsolitaire", "Zoryan", 5, new Date()));

        assertEquals(5, service.getRating("pegsolitaire", "Zoryan"));
    }

    @Test
    public void testUpdateRating() {
        service.reset();

        service.setRating(new Rating("pegsolitaire", "Zoryan", 3, new Date()));
        service.setRating(new Rating("pegsolitaire", "Zoryan", 1, new Date()));

        assertEquals(1, service.getRating("pegsolitaire", "Zoryan"));
    }

    @Test
    public void testAverageRating() {
        service.reset();

        service.setRating(new Rating("pegsolitaire", "A", 3, new Date()));
        service.setRating(new Rating("pegsolitaire", "B", 5, new Date()));

        assertEquals(4.0, service.getAverageRating("pegsolitaire"), 0.001);
    }

    @Test
    public void testReset() {
        service.setRating(new Rating("pegsolitaire", "A", 3, new Date()));

        service.reset();

        assertEquals(0, service.getRating("pegsolitaire", "A"));
    }
}
