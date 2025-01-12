package com.tntteam.tntdropbox.services;
import com.tntteam.tntdropbox.dtos.NotificationInputDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.Notification;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.NotificationRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Page<Notification> getAllNotifications(
            long userId,
            int page,
            int size,
            String query,
            String sortBy,
            String sortDirection)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(query == null) query = "";
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return notificationRepository.findByUserIdAndBodyContainingIgnoreCase(
                userId, query, pageable
        );
    }
    public Notification viewNotification(long notificationId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (user != notification.getUser()) {
            throw new ForbiddenException("You do not have permission to view this notification");
        }
        notification.setViewedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        return notification;
    }
    public Notification sendNotification(long userId, NotificationInputDTO notificationInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(notificationInput.getTitle());
        notification.setBody(notificationInput.getBody());
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        return notification;
    }
    public void deleteAllNotifications(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        notificationRepository.deleteByUserId(userId);
    }
    public void deleteNotification(long notificationId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (user != notification.getUser()) {
            throw new ForbiddenException("You do not have permission to delete this notification");
        }
        notificationRepository.delete(notification);
    }
}
