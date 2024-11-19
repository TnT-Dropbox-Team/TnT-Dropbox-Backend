package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String getAllUsers() {
        return "Seznam vseh uporabnikov";
    }
    public String getUser(long id) {
        return "Uporabnik: " + id;
    }
    public String createNewProfile() {
        return "ustvari profil";
    }
    public String login() {
        return "login";
    }
    public String updateUserProfile(long id) {
        return "Posodobi podatke za uporabnika: " + id;
    }
    public String deleteUserProfile(long id) {
        return "Izbriši progil: " + id;
    }
}
