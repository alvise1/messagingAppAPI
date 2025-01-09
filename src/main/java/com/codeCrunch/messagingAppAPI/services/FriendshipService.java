package com.codeCrunch.messagingAppAPI.services;

import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.models.Friendship;
import com.codeCrunch.messagingAppAPI.models.Friendship.FriendshipStatus;
import com.codeCrunch.messagingAppAPI.repositories.FriendshipRepository;
import com.codeCrunch.messagingAppAPI.repositories.AppUserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final AppUserRepository appUserRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, AppUserRepository appUserRepository) {
        this.friendshipRepository = friendshipRepository;
        this.appUserRepository = appUserRepository;
    }

    /**
     * Send a friend request from user1Id (sender) to user2Id (receiver).
     * Status: PENDING
     */
    public Friendship sendFriendRequest(Long user1Id, Long user2Id) {
        if (user1Id.equals(user2Id)) {
            throw new RuntimeException("Cannot send a friend request to yourself");
        }

        AppUser user1 = appUserRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User1 not found"));
        AppUser user2 = appUserRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        Friendship existingFriendship = friendshipRepository.findFriendshipBetween(user1Id, user2Id);
        if (existingFriendship != null) {
            throw new RuntimeException("Friendship already exists");
        }

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);  // Sender
        friendship.setUser2(user2);  // Recipient
        friendship.setStatus(FriendshipStatus.PENDING);

        return friendshipRepository.save(friendship);
    }

    /**
     * Accept or reject a PENDING friend request.
     * - Only the recipient (user2) can accept or reject.
     */
    public Friendship respondToFriendRequest(Long friendshipId, Long actingUserId, FriendshipStatus newStatus) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new RuntimeException("Cannot respond to a request that is not PENDING");
        }

        if (!friendship.getUser2().getId().equals(actingUserId)) {
            throw new RuntimeException("Only the recipient can respond to the friend request");
        }

        if (newStatus == FriendshipStatus.ACCEPTED) {
            friendship.setStatus(FriendshipStatus.ACCEPTED);
        } else if (newStatus == FriendshipStatus.REJECTED) {
            friendship.setStatus(FriendshipStatus.REJECTED);
        } else {
            throw new RuntimeException("Invalid status transition. Only ACCEPTED or REJECTED allowed here.");
        }

        return friendshipRepository.save(friendship);
    }

    /**
     * Cancel a PENDING friend request by the sender (user1).
     * - Changes status to CANCELED, but keeps the record in the DB.
     */
    public Friendship cancelFriendRequest(Long friendshipId, Long actingUserId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new RuntimeException("Cannot cancel a request that is not PENDING");
        }

        if (!friendship.getUser1().getId().equals(actingUserId)) {
            throw new RuntimeException("Only the sender can cancel the friend request");
        }

        friendship.setStatus(FriendshipStatus.CANCELED);
        return friendshipRepository.save(friendship);
    }

    /**
     * Block a user in an existing friendship.
     * - Either user can block the other at any time.
     * - Sets status to BLOCKED, keeps the record in the DB.
     */
    public Friendship blockFriend(Long friendshipId, Long actingUserId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!friendship.isUserPartOfFriendship(actingUserId)) {
            throw new RuntimeException("User is not part of this friendship");
        }

        if (friendship.getStatus() == FriendshipStatus.BLOCKED) {
            throw new RuntimeException("Friendship is already blocked");
        }

        friendship.setStatus(FriendshipStatus.BLOCKED);
        return friendshipRepository.save(friendship);
    }

    /**
     * Remove a friend. We will physically remove the friendship record from the DB.
     */
    public void removeFriend(Long friendshipId, Long actingUserId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!friendship.isUserPartOfFriendship(actingUserId)) {
            throw new RuntimeException("User is not part of this friendship");
        }

        friendshipRepository.delete(friendship);
    }

    /**
     * Get all friendships for a given user.
     */
    public List<Friendship> getAllFriendships(Long userId) {
        return friendshipRepository.findByUserId(userId);
    }
}
