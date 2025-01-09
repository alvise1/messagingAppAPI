package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private String status;
    private LocalDateTime timestamp;

    public MessageDTO() {
    }

    public MessageDTO(Long id, Long chatId, Long senderId,
                      String content, String status, LocalDateTime timestamp) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
