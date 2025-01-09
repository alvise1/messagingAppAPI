package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.UserResponseDTO;
import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody AppUser user) {
        AppUser registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(mapToDTO(registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AppUser user) {
        // Returns a mock token for now
        String token = userService.loginUser(user);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateUserProfile(@RequestBody AppUser user) {
        AppUser updatedUser = userService.updateUserProfile(user);
        return ResponseEntity.ok(mapToDTO(updatedUser));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    private UserResponseDTO mapToDTO(AppUser user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePic(user.getProfilePic());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole().name());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
