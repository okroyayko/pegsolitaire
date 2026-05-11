package sk.tuke.kpi.kp.pegsolitaire.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sk.tuke.kpi.kp.pegsolitaire.core.Field;
import sk.tuke.kpi.kp.pegsolitaire.core.Tile;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;
import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;

import java.util.Date;

@Controller
@RequestMapping("/pegsolitaire")
public class PegSolitaireController {

    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;

    public PegSolitaireController(ScoreService scoreService,
                                  CommentService commentService,
                                  RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    private Field getField(HttpSession session) {
        Field field = (Field) session.getAttribute("field");
        if (field == null) {
            field = new Field(7, 7);
            field.readFile("level1.txt");
            session.setAttribute("field", field);
        }
        return field;
    }

    @GetMapping
    public String gamePage(@RequestParam(defaultValue = "pegsolitaire") String game,
                           HttpSession session,
                           Model model) {

        // Якщо не залогінений → на логін
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // Поле
        Field field = getField(session);
        Tile[][] tiles = field.getTiles();
        model.addAttribute("board", tiles);

        // Дані для панелей
        model.addAttribute("game", game);
        model.addAttribute("scores", scoreService.getTopScores(game));
        model.addAttribute("comments", commentService.getComments(game));

        int avg = 0;
        try {
            avg = ratingService.getAverageRating(game);
        } catch (Exception ignored) {}
        model.addAttribute("averageRating", avg);

        // Ім’я гравця тепер береться з сесії
        model.addAttribute("user", session.getAttribute("user"));

        return "pegsolitaire";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String game,
                             @RequestParam String text,
                             HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) user = "Anonymous";

        commentService.addComment(new Comment(game, user, text, new Date()));

        return "redirect:/pegsolitaire?game=" + game;
    }

    @PostMapping("/rate")
    public String rate(@RequestParam String game,
                       @RequestParam int rating,
                       HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) user = "Anonymous";

        ratingService.setRating(new Rating(game, user, rating, new Date()));

        return "redirect:/pegsolitaire?game=" + game;
    }

    @PostMapping("/new")
    public String newGame(HttpSession session) {
        session.removeAttribute("field");
        return "redirect:/pegsolitaire";
    }
}
