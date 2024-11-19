package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public String getAllNotifications() {
        return "Seznam vseh obvestil";
    }
    public String getNotification(long id) {
        return "Vsebina obvestila: " + id;
    }
    public String sendNotificationToAll() {
        return "pošlji obvestilo vsem uporabnikom";
    }
    public String sendNotification(long userId) {
        return "pošlji obvestilo uporabniku: " + userId;
    }
    public String deleteAllNotifications() {
        return "izbriši vsa obvestila";
    }
    public String deleteNotification(long id) {
        return "izbriši obvestilo: " + id;
    }
}
