package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String index(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping
    public String performRegistration(@ModelAttribute("user") User user, @RequestParam("roles") Set<String> roles) {
        user.setRoles(changeStringToRole(roles));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/update")
    public String updateUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "upd";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<String> roles) {
        user.setRoles(changeStringToRole(roles));
        userService.update(user);
        return "redirect:/admin/users";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    private Set<Role> changeStringToRole(Set<String> strRoles) {
        return strRoles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }
}
