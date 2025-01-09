package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private String type;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;

    public NotificationDTO() {}

    public NotificationDTO (
            String type,
            String content,
            boolean isRead,
            LocalDateTime createdAt
    ) {
        this.type = type;
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public void setType(String name) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
