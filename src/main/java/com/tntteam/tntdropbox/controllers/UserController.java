package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public String getAllUsers() {
        return "Seznam vseh uporabnikov";
    }
    @GetMapping("/{id}")
    public String getUser(@PathVariable long id) {
        return "Uporabnik: " + id;
    }
    @PostMapping()
    public String createNewProfile() {
        return "ustvari profil";
    }
    @PostMapping("/login")
    public String login() {
        return "login";
    }
    @PutMapping("/{id}")
    public String updateUserProfile(@PathVariable long id) {
        return "Posodobi podatke za uporabnika: " + id;
    }
    @DeleteMapping("/{id}")
    public String deleteUserProfile(@PathVariable long id) {
        return "Izbriši progil: " + id;
    }
}
