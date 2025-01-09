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
    @Size(max = 2048)    // We'll limit single text content for now
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id", referencedColumnName = "id")
    private Attachment attachment;

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

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
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
