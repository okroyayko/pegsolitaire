package sk.tuke.kpi.kp.pegsolitaire.service.rest;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService {

    private static final String URL = "http://localhost:8083/api/comment";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(URL, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) {
        Comment[] comments = restTemplate.getForObject(URL + "/" + game, Comment[].class);
        return Arrays.asList(comments);
    }

    @Override
    public void reset() {
        restTemplate.delete(URL);
    }
}
