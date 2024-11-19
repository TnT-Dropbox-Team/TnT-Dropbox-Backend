package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public String getAllNotifications() {
        return notificationService.getAllNotifications();
    }
    @GetMapping("/{id}")
    public String getNotification(@PathVariable long id) {
        return notificationService.getNotification(id);
    }
    @PostMapping()
    public String sendNotificationToAll() {
        return notificationService.sendNotificationToAll();
    }
    @PostMapping("/{userId}")
    public String sendNotification(@PathVariable long userId) {
        return notificationService.sendNotification(userId);
    }
    @DeleteMapping()
    public String deleteAllNotifications() {
        return notificationService.deleteAllNotifications();
    }
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable long id) {
        return notificationService.deleteNotification(id);
    }
}
