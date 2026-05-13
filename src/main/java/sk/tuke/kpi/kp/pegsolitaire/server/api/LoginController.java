package sk.tuke.kpi.kp.pegsolitaire.server.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.pegsolitaire.service.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {
        if (!userService.userExists(username)) {
            return "redirect:/login?error=no_user";
        }

        if (!userService.passwordMatches(username, password)) {
            return "redirect:/login?error=bad_password";
        }

        session.setAttribute("user", username);

        return "redirect:/pegsolitaire";
    }
}
