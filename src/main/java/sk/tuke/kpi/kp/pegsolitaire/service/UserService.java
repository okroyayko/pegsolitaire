package sk.tuke.kpi.kp.pegsolitaire.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JdbcTemplate jdbc;

    public UserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean userExists(String username) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ?",
                Integer.class,
                username
        );
        return count != null && count > 0;
    }

    public boolean passwordMatches(String username, String password) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ? AND password_hash = ?",
                Integer.class,
                username, password
        );
        return count != null && count == 1;
    }

    public void register(String username, String password) {
        jdbc.update(
                "INSERT INTO users(username, password_hash) VALUES (?, ?)",
                username, password
        );
    }
}
