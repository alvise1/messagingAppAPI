package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {
    private String username;
    private String email;
    private String bio;
    private String profilePic;
    private String status;
    private String role;
    private LocalDateTime updatedAt;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String username, String email, String bio,
                           String profilePic, String status, String role, LocalDateTime updatedAt) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profilePic = profilePic;
        this.status = status;
        this.role = role;
        this.updatedAt = updatedAt;
    }

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setBio(String bio) { this.bio = bio; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public void setStatus(String status) { this.status = status; }

    public void setRole(String role) { this.role = role; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
