package SpringBoot.controller;

import SpringBoot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("Users", List.of(userService.findUserByUsername(principal.getName())));
        return "user";
    }
}
