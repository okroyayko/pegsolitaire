package sk.tuke.kpi.kp.pegsolitaire.server.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.pegsolitaire.core.Field;

@RestController
@RequestMapping("/api/game")
public class GameRestController {

    private Field createField() {
        Field f = new Field(7, 7);
        f.readFile("level1.txt");
        return f;
    }

    @GetMapping("/state")
    public Field getState(HttpSession session) {
        Field field = (Field) session.getAttribute("field");
        if (field == null) {
            field = createField();
            session.setAttribute("field", field);
        }
        return field;
    }

    @PostMapping("/new")
    public Field newGame(
            @RequestParam(required = false, defaultValue = "level1.txt") String level,
            HttpSession session
    ) {
        Field field = new Field(7, 7);
        field.readFile(level);
        session.setAttribute("field", field);
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
            field = createField();
            session.setAttribute("field", field);
        }
        field.move(r1, c1, r2, c2);
        return field;
    }
}
