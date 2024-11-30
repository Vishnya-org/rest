package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user,
                       @RequestParam(value = "roles", required = false) Set<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            user.setRoles(changeStringToRole(roles));
        }
        userService.save(user);
        return userService.findById(user.getId());
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user, @RequestParam(name = "roles", required = false) Set<String> roles) {
        user.setId(id);
        if (roles != null && !roles.isEmpty()) {
            user.setRoles(changeStringToRole(roles));
        }
        userService.update(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    private Set<Role> changeStringToRole(Set<String> strRoles) {
        return strRoles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }
}
