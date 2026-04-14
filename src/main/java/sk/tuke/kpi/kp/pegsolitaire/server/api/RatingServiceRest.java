package sk.tuke.kpi.kp.pegsolitaire.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import sk.tuke.kpi.kp.pegsolitaire.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }

    @GetMapping("/average/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @DeleteMapping
    public void reset() {
        ratingService.reset();
    }
}
