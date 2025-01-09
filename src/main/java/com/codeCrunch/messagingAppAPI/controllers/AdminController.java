package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.UserResponseDTO;
import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<AppUser> users = userService.getAllUsers();
        List<UserResponseDTO> dtoList = users.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Lock user account.
     */
    @PutMapping("/users/{userId}/lock")
    public ResponseEntity<UserResponseDTO> lockUser(@PathVariable Long userId) {
        AppUser lockedUser = userService.lockUser(userId);
        return ResponseEntity.ok(mapToDTO(lockedUser));
    }

    /**
     * Unlock user account.
     */
    @PutMapping("/users/{userId}/unlock")
    public ResponseEntity<UserResponseDTO> unlockUser(@PathVariable Long userId) {
        AppUser unlockedUser = userService.unlockUser(userId);
        return ResponseEntity.ok(mapToDTO(unlockedUser));
    }

    /**
     * Disable user (setEnabled(false)).
     */
    @PutMapping("/users/{userId}/disable")
    public ResponseEntity<UserResponseDTO> disableUser(@PathVariable Long userId) {
        AppUser disabledUser = userService.disableUser(userId);
        return ResponseEntity.ok(mapToDTO(disabledUser));
    }

    /**
     * Enable user.
     */
    @PutMapping("/users/{userId}/enable")
    public ResponseEntity<UserResponseDTO> enableUser(@PathVariable Long userId) {
        AppUser enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(mapToDTO(enabledUser));
    }

    /**
     * Expire user credentials (setCredentialsExpired(true)).
     */
    @PutMapping("/users/{userId}/expire-credentials")
    public ResponseEntity<UserResponseDTO> expireCredentials(@PathVariable Long userId) {
        AppUser user = userService.expireUserCredentials(userId);
        return ResponseEntity.ok(mapToDTO(user));
    }

    /**
     * Unexpire user credentials (setCredentialsExpired(false)).
     */
    @PutMapping("/users/{userId}/unexpire-credentials")
    public ResponseEntity<UserResponseDTO> unexpireCredentials(@PathVariable Long userId) {
        AppUser user = userService.unexpireUserCredentials(userId);
        return ResponseEntity.ok(mapToDTO(user));
    }

    /**
     * Expire entire user account (setAccountExpired(true)).
     */
    @PutMapping("/users/{userId}/expire-account")
    public ResponseEntity<UserResponseDTO> expireAccount(@PathVariable Long userId) {
        AppUser user = userService.expireUserAccount(userId);
        return ResponseEntity.ok(mapToDTO(user));
    }

    /**
     * Unexpire entire user account (setAccountExpired(false)).
     */
    @PutMapping("/users/{userId}/unexpire-account")
    public ResponseEntity<UserResponseDTO> unexpireAccount(@PathVariable Long userId) {
        AppUser user = userService.unexpireUserAccount(userId);
        return ResponseEntity.ok(mapToDTO(user));
    }

    // Maps AppUser to UserResponseDTO
    private UserResponseDTO mapToDTO(AppUser user) {
        if (user == null) return null;

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
