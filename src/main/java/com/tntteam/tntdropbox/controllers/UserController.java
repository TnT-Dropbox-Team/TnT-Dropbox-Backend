package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public String getUser(@PathVariable long id) {
        return userService.getUser(id);
    }
    @PostMapping()
    public String createNewProfile() {
        return userService.createNewProfile();
    }
    @PostMapping("/login")
    public String login() {
        return userService.login();
    }
    @PutMapping("/{id}")
    public String updateUserProfile(@PathVariable long id) {
        return userService.updateUserProfile(id);
    }
    @DeleteMapping("/{id}")
    public String deleteUserProfile(@PathVariable long id) {
        return userService.deleteUserProfile(id);
    }
}
