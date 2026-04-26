package sk.tuke.kpi.kp.pegsolitaire.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import sk.tuke.kpi.kp.pegsolitaire.service.CommentService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceJPATest {

    @Autowired
    private CommentService service;

    @Test
    public void testAddAndGetComments() {
        service.reset();

        Comment c = new Comment("pegsolitaire", "Zoryan", "Good game", new Date());
        service.addComment(c);

        List<Comment> list = service.getComments("pegsolitaire");

        assertEquals(1, list.size());
        assertEquals("Zoryan", list.get(0).getPlayer());
        assertEquals("Good game", list.get(0).getComment());
    }

    @Test
    public void testReset() {
        service.addComment(new Comment("pegsolitaire", "A", "Hi", new Date()));
        service.addComment(new Comment("pegsolitaire", "B", "Hello", new Date()));

        service.reset();

        assertEquals(0, service.getComments("pegsolitaire").size());
    }
}
