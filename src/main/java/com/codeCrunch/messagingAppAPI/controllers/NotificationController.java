package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.Notification;
import com.codeCrunch.messagingAppAPI.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Fetch all notifications for a user
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(@RequestParam Long userId) {
        List<Notification> notifications = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // Mark a notification as read
    @PutMapping("/{notificationId}")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable Long notificationId) {
        Notification updatedNotification = notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok(updatedNotification);
    }
}