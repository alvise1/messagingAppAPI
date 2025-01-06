package com.codeCrunch.messagingAppAPI.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // How will this value be set?
    private boolean isGroup;

    private String groupName;   //Only for group chats

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private Message lastMessage;

    @OneToMany(mappedBy = "chat")
    private Set<UserChat> participants = new HashSet<>();    // List of participants in chat

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Message getLastMessage() {
        return lastMessage;
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
}
