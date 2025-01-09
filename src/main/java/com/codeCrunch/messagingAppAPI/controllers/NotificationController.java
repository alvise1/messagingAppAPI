package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.NotificationDTO;
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

    /**
     * Fetch all notifications for a user (return as DTO list).
     */
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@RequestParam Long userId) {
        List<Notification> notifications = notificationService.getAllNotifications(userId);
        List<NotificationDTO> dtos = notifications.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Mark a notification as read (return updated notification as DTO).
     */
    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable Long notificationId) {
        Notification updated = notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok(mapToDTO(updated));
    }

    // ============= MAPPING UTILITIES =============

    private NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setType(notification.getType().name());
        dto.setContent(notification.getContent());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}
