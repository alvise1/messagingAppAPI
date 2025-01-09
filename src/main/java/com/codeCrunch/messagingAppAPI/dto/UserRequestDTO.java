package com.codeCrunch.messagingAppAPI.dto;

public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String profilePic;
    private String status;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String username, String email, String password, String bio, String profilePic, String status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.profilePic = profilePic;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getStatus() {
        return status;
    }
}
