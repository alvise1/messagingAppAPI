package com.codeCrunch.messagingAppAPI.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"}))
@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private AppUser user1;      // First user in friendship

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private AppUser user2;      // Second user in friendship

    public enum FriendshipStatus {
       PENDING,
       ACCEPTED
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser1() {
        return user1;
    }

    public void setUser1(AppUser user1) {
        this.user1 = user1;
    }

    public AppUser getUser2() {
        return user2;
    }

    public void setUser2(AppUser user2) {
        this.user2 = user2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
