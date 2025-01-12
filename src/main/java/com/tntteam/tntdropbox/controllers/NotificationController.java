package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.NotificationInputDTO;
import com.tntteam.tntdropbox.models.Notification;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Profile({"notification", "test"})
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping()
    public Page<Notification> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationService.getAllNotifications(user.getId(), page, size, query, sortBy, sortDirection);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/{id}")
    public Notification viewNotification(@PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationService.viewNotification(id, user.getId());
    }

    @PostMapping("/to/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Notification sendNotification(@PathVariable long userId, @Valid @RequestBody NotificationInputDTO notificationInput) {
        return notificationService.sendNotification(userId, notificationInput);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllNotifications() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notificationService.deleteAllNotifications(user.getId());
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        notificationService.deleteNotification(id, user.getId());
    }
}
