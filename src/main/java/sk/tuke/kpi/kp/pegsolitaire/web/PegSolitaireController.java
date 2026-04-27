package sk.tuke.kpi.kp.pegsolitaire.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sk.tuke.kpi.kp.pegsolitaire.core.Field;
import sk.tuke.kpi.kp.pegsolitaire.core.Tile;
import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import sk.tuke.kpi.kp.pegsolitaire.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/pegsolitaire")
public class PegSolitaireController {

    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;

    private final Field field;

    public PegSolitaireController(ScoreService scoreService,
                                  CommentService commentService,
                                  RatingService ratingService) {

        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;

        this.field = new Field(7, 7);

        field.readFile("level1.txt");
    }

    @GetMapping
    public String gamePage(@RequestParam(defaultValue = "pegsolitaire") String game,
                           @RequestParam(required = false) String player,
                           Model model) {

        Tile[][] tiles = new Tile[field.getRowCount()][field.getColumnCount()];
        for (int r = 0; r < field.getRowCount(); r++) {
            for (int c = 0; c < field.getColumnCount(); c++) {
                tiles[r][c] = field.getTile(r, c);
            }
        }
        model.addAttribute("board", tiles);

        model.addAttribute("game", game);
        model.addAttribute("scores", scoreService.getTopScores(game));
        model.addAttribute("comments", commentService.getComments(game));

        int avg = 0;
        try {
            avg = ratingService.getAverageRating(game);
        } catch (Exception ignored) {}
        model.addAttribute("averageRating", avg);

        model.addAttribute("player", player == null ? "" : player);

        return "pegsolitaire";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String game,
                             @RequestParam String player,
                             @RequestParam String text) {

        commentService.addComment(new Comment(game, player, text, new Date()));
        return "redirect:/pegsolitaire?game=" + game + "&player=" + player;
    }

    @PostMapping("/rate")
    public String rate(@RequestParam String game,
                       @RequestParam String player,
                       @RequestParam int rating) {

        ratingService.setRating(new Rating(game, player, rating, new Date()));
        return "redirect:/pegsolitaire?game=" + game + "&player=" + player;
    }
}
