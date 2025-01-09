package com.codeCrunch.messagingAppAPI.repositories;

import com.codeCrunch.messagingAppAPI.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Retrieves all Messages for the given chat (by chatId),
     * ordered by ascending timestamp.
     */
    List<Message> findByChatIdOrderByTimestampAsc(Long chatId);
}
