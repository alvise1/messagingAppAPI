package com.codeCrunch.messagingAppAPI.repositories;

import com.codeCrunch.messagingAppAPI.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.user.id = :userId")
    List<Chat> findByParticipantsUserId(@Param("userId") Long userId);
}