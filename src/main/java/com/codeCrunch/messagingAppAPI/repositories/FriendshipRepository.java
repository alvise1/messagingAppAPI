package com.codeCrunch.messagingAppAPI.repositories;

import com.codeCrunch.messagingAppAPI.models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.user1.id = :user1Id AND f.user2.id = :user2Id) " +
            "   OR (f.user1.id = :user2Id AND f.user2.id = :user1Id)")
    Friendship findFriendshipBetween(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT f FROM Friendship f WHERE f.user1.id = :userId OR f.user2.id = :userId")
    List<Friendship> findByUserId(@Param("userId") Long userId);
}
