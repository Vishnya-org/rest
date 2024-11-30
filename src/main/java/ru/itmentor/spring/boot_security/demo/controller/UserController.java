package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUserById(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.findById(user.getId());
    }

    @PatchMapping()
    public User updateUser(Authentication authentication, @RequestBody User updUser) {
        User user = (User) authentication.getPrincipal();
        updUser.setId(user.getId());
        userService.update(updUser);
        return userService.findById(updUser.getId());
    }
}