package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String profilePic;
    private String status;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String email, String bio,
                           String profilePic, String status, String role,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profilePic = profilePic;
        this.status = status;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) { this.bio = bio; }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
