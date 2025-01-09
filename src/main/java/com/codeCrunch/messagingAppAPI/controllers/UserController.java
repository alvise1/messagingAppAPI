package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.AppUser;
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
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser user) {
        AppUser registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AppUser user) {
        String token = userService.loginUser(user);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/profile")
    public ResponseEntity<AppUser> updateUserProfile(@RequestBody AppUser user) {
        AppUser updatedUser = userService.updateUserProfile(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
