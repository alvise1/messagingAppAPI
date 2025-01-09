package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.FriendshipDTO;
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

    /**
     * Send a new friend request (PENDING).
     */
    @PostMapping
    public ResponseEntity<FriendshipDTO> sendFriendRequest(@RequestBody FriendshipDTO friendshipDTO) {
        Long user1Id = friendshipDTO.getUser1Id();
        Long user2Id = friendshipDTO.getUser2Id();
        Friendship sentRequest = friendshipService.sendFriendRequest(user1Id, user2Id);
        return ResponseEntity.ok(mapToDTO(sentRequest));
    }

    /**
     * Respond to a PENDING friend request: ACCEPT or REJECT.
     * - You can pass the new status in the request body (friendshipDTO).
     * - The acting user ID should be supplied as a request param or path variable.
     */
    @PutMapping("/{friendshipId}/respond")
    public ResponseEntity<FriendshipDTO> respondToFriendRequest(
            @PathVariable Long friendshipId,
            @RequestParam Long actingUserId,
            @RequestBody FriendshipDTO friendshipDTO) {

        Friendship updatedFriendship = friendshipService.respondToFriendRequest(
                friendshipId,
                actingUserId,
                friendshipDTO.getStatus() // either ACCEPTED or REJECTED
        );
        return ResponseEntity.ok(mapToDTO(updatedFriendship));
    }

    /**
     * Cancel a PENDING request by the sender (changes status to CANCELED).
     */
    @PutMapping("/{friendshipId}/cancel")
    public ResponseEntity<FriendshipDTO> cancelFriendRequest(
            @PathVariable Long friendshipId,
            @RequestParam Long actingUserId) {

        Friendship canceledFriendship = friendshipService.cancelFriendRequest(friendshipId, actingUserId);
        return ResponseEntity.ok(mapToDTO(canceledFriendship));
    }

    /**
     * Block a friend (sets status to BLOCKED).
     */
    @PutMapping("/{friendshipId}/block")
    public ResponseEntity<FriendshipDTO> blockFriend(
            @PathVariable Long friendshipId,
            @RequestParam Long actingUserId) {

        Friendship blockedFriendship = friendshipService.blockFriend(friendshipId, actingUserId);
        return ResponseEntity.ok(mapToDTO(blockedFriendship));
    }

    /**
     * Remove a friend entirely from the database (delete the record).
     */
    @DeleteMapping("/{friendshipId}/remove")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long friendshipId,
            @RequestParam Long actingUserId) {

        friendshipService.removeFriend(friendshipId, actingUserId);
        return ResponseEntity.ok("Friend removed successfully");
    }

    /**
     * Get all friendships for a given user.
     */
    @GetMapping
    public ResponseEntity<List<FriendshipDTO>> getAllFriendships(@RequestParam Long userId) {
        List<Friendship> friendships = friendshipService.getAllFriendships(userId);
        List<FriendshipDTO> friendshipDTOs = friendships.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(friendshipDTOs);
    }

    /**
     * Utility method to map a Friendship entity to a FriendshipDTO.
     */
    private FriendshipDTO mapToDTO(Friendship friendship) {
        FriendshipDTO dto = new FriendshipDTO();
        dto.setUser1Id(friendship.getUser1().getId());
        dto.setUser2Id(friendship.getUser2().getId());
        dto.setStatus(friendship.getStatus());
        dto.setUpdatedAt(friendship.getUpdatedAt());
        return dto;
    }
}
