package sk.tuke.kpi.kp.pegsolitaire.server.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sk.tuke.kpi.kp.pegsolitaire.core.Field;
import sk.tuke.kpi.kp.pegsolitaire.core.GameState;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;

import java.util.Date;

@RestController
@RequestMapping("/api/game")
public class GameRestController {

    @Autowired
    private ScoreService scoreService;

    private Field createField(String level) {
        Field f = new Field(7, 7);
        f.readFile(level);
        return f;
    }

    private void saveScoreIfSolved(Field field, HttpSession session) {
        if (field.getGameState() == GameState.SOLVED) {

            int remaining = field.getRemainingPegs();
            int points = 100 - remaining;

            if (points <= 0) return;

            String player = (String) session.getAttribute("user");
            if (player == null || player.isBlank()) player = "Anonymous";

            Score score = new Score(
                    player,
                    "pegsolitaire",
                    points,
                    new Date()
            );

            scoreService.addScore(score);
        }
    }


    @GetMapping("/state")
    public Field getState(HttpSession session) {

        Field field = (Field) session.getAttribute("field");
        if (field == null) {
            field = createField("level1.txt");
            session.setAttribute("field", field);
        }

        return field;
    }

    @PostMapping("/move")
    public Field move(
            @RequestParam int r1,
            @RequestParam int c1,
            @RequestParam int r2,
            @RequestParam int c2,
            HttpSession session
    ) {
        Field field = (Field) session.getAttribute("field");
        if (field == null) {
            field = createField("level1.txt");
            session.setAttribute("field", field);
        }

        field.move(r1, c1, r2, c2);

        saveScoreIfSolved(field, session);

        return field;
    }

    @PostMapping("/new")
    public Field newGame(
            @RequestParam(required = false, defaultValue = "level1.txt") String level,
            HttpSession session
    ) {
        Field field = createField(level);
        session.setAttribute("field", field);
        return field;
    }
}
