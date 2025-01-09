package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.UserRequestDTO;
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

    /**
     * Register a new user (receives request DTO, returns response DTO).
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userDTO) {
        AppUser userEntity = mapToEntity(userDTO);
        AppUser registeredUser = userService.registerUser(userEntity);
        return ResponseEntity.ok(mapToResponseDTO(registeredUser));
    }

    /**
     * Login user (returns a mock token).
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequestDTO userDTO) {
        AppUser userEntity = mapToEntity(userDTO);
        String token = userService.loginUser(userEntity);
        return ResponseEntity.ok(token);
    }

    /**
     * Update user profile (receives request DTO, returns response DTO).
     */
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateUserProfile(@RequestBody UserRequestDTO userDTO) {
        AppUser userEntity = mapToEntity(userDTO);
        AppUser updatedUser = userService.updateUserProfile(userEntity);
        return ResponseEntity.ok(mapToResponseDTO(updatedUser));
    }

    /**
     * Delete a user by ID.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    // ============== MAPPING UTILS ==============

    /**
     * Map from UserRequestDTO -> AppUser entity.
     */
    private AppUser mapToEntity(UserRequestDTO dto) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBio(dto.getBio());
        user.setProfilePic(dto.getProfilePic());
        user.setStatus(dto.getStatus());
        return user;
    }

    /**
     * Map from AppUser -> UserResponseDTO.
     */
    private UserResponseDTO mapToResponseDTO(AppUser user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePic(user.getProfilePic());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole().name());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
