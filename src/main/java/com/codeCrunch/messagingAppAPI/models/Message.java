package com.codeCrunch.messagingAppAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;  // Chat this message belongs to

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private AppUser sender;     // The user who sent this message

    @Column(nullable = false)
    @Size(max = 1024)    // We'll limit single text content for now
    private String content;

    private String attachmentUrl;   // Optional text url of attachment media

    private String attachmentType;  // What file type is the attachment

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;    // When message was sent

    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.SENT;      // Default value of message

    public enum MessageStatus {
        SENT,
        DELIVERED,
        READ
    }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setSender(AppUser sender) {
        this.sender = sender;
    }

    public AppUser getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
