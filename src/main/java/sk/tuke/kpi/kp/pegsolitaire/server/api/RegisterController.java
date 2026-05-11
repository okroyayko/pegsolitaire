package sk.tuke.kpi.kp.pegsolitaire.server.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.pegsolitaire.service.UserService;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {
        if (userService.userExists(username)) {
            return "redirect:/register?error=exists";
        }

        userService.register(username, password);
        session.setAttribute("user", username);

        return "redirect:/pegsolitaire";
    }
}
