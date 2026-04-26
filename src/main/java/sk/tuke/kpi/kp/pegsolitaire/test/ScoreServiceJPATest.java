package sk.tuke.kpi.kp.pegsolitaire.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScoreServiceJPATest {

    @Autowired
    private ScoreService service;

    @Test
    public void testAddScoreAndGetScores() {
        service.reset();

        Score s = new Score("Zoryan", "pegsolitaire", 50, new Date());
        service.addScore(s);

        List<Score> list = service.getScores("pegsolitaire");

        assertEquals(1, list.size());
        assertEquals("Zoryan", list.get(0).getPlayer());
        assertEquals(50, list.get(0).getPoints());
    }

    @Test
    public void testTopScores() {
        service.reset();

        service.addScore(new Score("A", "pegsolitaire", 10, new Date()));
        service.addScore(new Score("B", "pegsolitaire", 70, new Date()));
        service.addScore(new Score("C", "pegsolitaire", 40, new Date()));

        List<Score> top = service.getTopScores("pegsolitaire");

        assertEquals(3, top.size());
        assertEquals(70, top.get(0).getPoints());
    }

    @Test
    public void testReset() {
        service.addScore(new Score("A", "pegsolitaire", 10, new Date()));
        service.addScore(new Score("B", "pegsolitaire", 20, new Date()));

        service.reset();

        assertEquals(0, service.getScores("pegsolitaire").size());
    }
}
