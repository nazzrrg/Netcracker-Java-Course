package com.github.nazzrrg.simplespringangularapp.controller;

import com.github.nazzrrg.simplespringangularapp.model.User;
import com.github.nazzrrg.simplespringangularapp.controller.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    void create(@RequestBody User user) {
        service.create(user);
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable long id) {
        return service.getById(id);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
