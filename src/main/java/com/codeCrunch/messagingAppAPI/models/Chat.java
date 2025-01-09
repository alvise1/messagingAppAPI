package com.codeCrunch.messagingAppAPI.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isGroup;

    private String groupName;   //Only for group chats

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private Message lastMessage;

    // List of participants in chat
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChat> participants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
        if (!isGroup) {
            this.groupName = null;
        }
    }

    public boolean getGroup() {
        return isGroup;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName(){
        return groupName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void addParticipant(UserChat userChat) {
        this.participants.add(userChat);
        userChat.setChat(this);
    }

    public void removeParticipant(UserChat userChat) {
        this.participants.remove(userChat);
        userChat.setChat(null);
    }

    public void setParticipants(Set<UserChat> participants) {
        if (this.participants == null) {
            this.participants = new HashSet<>();
        }
        if (participants != null) {
            this.participants.addAll(participants);
        }
    }

    public Set<UserChat> getParticipants() {
        return Collections.unmodifiableSet(participants);
    }

    private void validateGroupChat() {
        if (this.isGroup && (this.groupName == null || this.groupName.trim().isEmpty())) {
            throw new IllegalArgumentException("Group Chat must have a name!");
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        validateGroupChat();
    }

    public Message getLastMessage() {
        return lastMessage;
    }
}
