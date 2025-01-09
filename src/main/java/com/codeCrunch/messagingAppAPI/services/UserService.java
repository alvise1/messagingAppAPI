package com.codeCrunch.messagingAppAPI.services;

import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.repositories.AppUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Register a new user.
     * 1) Hash the password before saving.
     * 2) Return the saved user (or a DTO).
     */
    public AppUser registerUser(AppUser user) {
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return appUserRepository.save(user);
    }

    /**
     * Login a user by checking credentials.
     * - Placeholder until JWT is set up
     */
    public String loginUser(AppUser user) {
        AppUser existingUser = appUserRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        // Return a mock token for now
        return "mock-jwt-token";
    }

    /**
     * Update user profile
     */
    public AppUser updateUserProfile(AppUser user) {
        AppUser existingUser = appUserRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setBio(user.getBio());
        existingUser.setProfilePic(user.getProfilePic());
        existingUser.setStatus(user.getStatus());
        existingUser.setEmail(user.getEmail());
        existingUser.setUpdatedAt(LocalDateTime.now());

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return appUserRepository.save(existingUser);
    }

    /**
     * Delete user by ID.
     */
    public void deleteUser(Long userId) {
        if (appUserRepository.existsById(userId)) {
            appUserRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // ========== Admin-Only Operations ==========

    /**
     * Create an admin user (ROLE.ADMIN).
     */
    public void createAdminUser(AppUser user) {
        user.setRole(AppUser.Role.ADMIN);

        // Hash the password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        appUserRepository.save(user);
    }

    /**
     * Check if an admin user with a specific email already exists.
     */
    public boolean adminExistsByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .filter(u -> u.getRole() == AppUser.Role.ADMIN)
                .isPresent();
    }

    /**
     * Get all users.
     */
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    /**
     * Lock a user's account (isAccountNonLocked() becomes false).
     */
    public AppUser lockUser(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setAccountLocked(true);
        return appUserRepository.save(user);
    }

    /**
     * Unlock a user's account.
     */
    public AppUser unlockUser(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setAccountLocked(false);
        return appUserRepository.save(user);
    }

    /**
     * Disable a user (isEnabled() becomes false).
     */
    public AppUser disableUser(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setEnabled(false);
        return appUserRepository.save(user);
    }

    /**
     * Enable a user.
     */
    public AppUser enableUser(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setEnabled(true);
        return appUserRepository.save(user);
    }

    /**
     * Expire a user's credentials (isCredentialsNonExpired() becomes false).
     */
    public AppUser expireUserCredentials(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setCredentialsExpired(true);
        return appUserRepository.save(user);
    }

    /**
     * Unexpire a user's credentials (isCredentialsNonExpired() becomes true).
     */
    public AppUser unexpireUserCredentials(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setCredentialsExpired(false);
        return appUserRepository.save(user);
    }

    /**
     * Mark a user's entire account as expired (isAccountNonExpired() becomes false).
     */
    public AppUser expireUserAccount(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setAccountExpired(true);
        return appUserRepository.save(user);
    }

    /**
     * Unexpire a user's entire account.
     */
    public AppUser unexpireUserAccount(Long userId) {
        AppUser user = getUserOrThrow(userId);
        user.setAccountExpired(false);
        return appUserRepository.save(user);
    }

    private AppUser getUserOrThrow(Long userId) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }
}
