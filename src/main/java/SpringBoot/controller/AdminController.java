package SpringBoot.controller;

import SpringBoot.service.UserService;
import SpringBoot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String admin(Model model, Principal principal) {
        List<User> list = userService.findAllUsers();
        model.addAttribute("Users", list);
        return "user";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        String role = "test";
        model.addAttribute("user", new User());
        model.addAttribute("Role", role);
        return "create";
    }

    @PostMapping(value = "/new")
    @Transactional
    public String addNewUser(@ModelAttribute("newUser") User user) {
        userService.add(user);
        return "redirect:/admin/";
    }

    @GetMapping("/edit/{id}")
    @Transactional
    public String edit(@PathVariable("id") long id, Model model){
        User user = userService.findUserById(id).get();
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/")
    @Transactional
    public String edit(@ModelAttribute("user") User user) {
        userService.edit(user);
        return "redirect:/admin/";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin/";
    }
}
