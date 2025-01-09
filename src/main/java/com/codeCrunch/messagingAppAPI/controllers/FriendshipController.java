package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.Friendship;
import com.codeCrunch.messagingAppAPI.services.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping
    public ResponseEntity<Friendship> sendFriendRequest(@RequestBody Friendship friendship) {
        Friendship sentRequest = friendshipService.sendFriendRequest(friendship);
        return ResponseEntity.ok(sentRequest);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Friendship> respondToFriendRequest(@PathVariable Long requestId, @RequestBody Friendship friendship) {
        Friendship updatedRequest = friendshipService.respondToFriendRequest(requestId, friendship);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> removeOrBlockFriend(@PathVariable Long friendId) {
        friendshipService.removeOrBlockFriend(friendId);
        return ResponseEntity.ok("Friend removed or blocked successfully");
    }

    @GetMapping
    public ResponseEntity<List<Friendship>> getAllFriendships(@RequestParam Long userId) {
        List<Friendship> friendships = friendshipService.getAllFriendships(userId);
        return ResponseEntity.ok(friendships);
    }
}
