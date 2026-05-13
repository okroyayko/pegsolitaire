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

    // MAIN PAGE
    @GetMapping
    public String gamePage(@RequestParam(defaultValue = "pegsolitaire") String game,
                           HttpSession session,
                           Model model) {

        String username = (String) session.getAttribute("user");

        // Якщо не увійшов → редірект на логін
        if (username == null) {
            return "redirect:/login";
        }

        // Rating
        int userRating = ratingService.getRating(game, username);
        model.addAttribute("userRating", userRating);
        model.addAttribute("averageRating", ratingService.getAverageRating(game));

        // Comment
        boolean hasComment = commentService.getCommentByPlayer(game, username) != null;
        model.addAttribute("hasComment", hasComment);

        // Game board
        Field field = getField(session);
        model.addAttribute("board", field.getTiles());

        // Other
        model.addAttribute("game", game);
        model.addAttribute("scores", scoreService.getTopScores(game));
        model.addAttribute("comments", commentService.getComments(game));
        model.addAttribute("user", username);

        return "pegsolitaire";
    }

    // ADD COMMENT
    @PostMapping("/comment")
    public String addComment(@RequestParam String game,
                             @RequestParam String text,
                             HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Comment existing = commentService.getCommentByPlayer(game, user);
        if (existing != null) {
            return "redirect:/pegsolitaire?game=" + game + "&error=already_commented";
        }

        commentService.addComment(new Comment(game, user, text, new Date()));
        return "redirect:/pegsolitaire?game=" + game;
    }

    // EDIT COMMENT
    @PostMapping("/comment/edit")
    public String editComment(@RequestParam String game,
                              @RequestParam String text,
                              HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Comment existing = commentService.getCommentByPlayer(game, user);
        if (existing != null) {
            existing.setComment(text);
            existing.setCommentedOn(new Date());
            commentService.updateComment(existing);
        }

        return "redirect:/pegsolitaire?game=" + game;
    }

    // DELETE COMMENT
    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam String game,
                                HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        commentService.deleteComment(game, user);

        return "redirect:/pegsolitaire?game=" + game;
    }

    // RATE
    @PostMapping("/rate")
    public String rate(@RequestParam(required = false) Integer rating,
                       @RequestParam String game,
                       HttpSession session) {

        String user = (String) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (rating == null || rating < 1 || rating > 5) {
            return "redirect:/pegsolitaire?error=invalidRating";
        }

        Rating r = new Rating();
        r.setGame(game);
        r.setPlayer(user);
        r.setRating(rating);
        r.setRatedOn(new Date());

        ratingService.setRating(r);

        return "redirect:/pegsolitaire?game=" + game;
    }

    // NEW GAME
    @PostMapping("/new")
    public String newGame(HttpSession session) {
        session.removeAttribute("field");
        return "redirect:/pegsolitaire";
    }
}
