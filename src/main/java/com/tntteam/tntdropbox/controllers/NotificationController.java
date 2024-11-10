package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @GetMapping()
    public String getAllNotifications() {
        return "Seznam vseh obvestil";
    }
    @GetMapping("/{id}")
    public String getNotification(@PathVariable long id) {
        return "Vsebina obvestila: " + id;
    }
    @PostMapping()
    public String sendNotificationToAll() {
        return "pošlji obvestilo vsem uporabnikom";
    }
    @PostMapping("/{UserId}")
    public String sendNotification(@PathVariable long UserId) {
        return "pošlji obvestilo uporabniku: " + UserId;
    }
    @DeleteMapping()
    public String deleteAllNotifications() {
        return "izbriši vsa obvestila";
    }
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable long id) {
        return "izbriši obvestilo: " + id;
    }
}
