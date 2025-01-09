package com.codeCrunch.messagingAppAPI.services;

import com.codeCrunch.messagingAppAPI.models.*;
import com.codeCrunch.messagingAppAPI.repositories.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final AppUserRepository appUserRepository;

    public ChatService(ChatRepository chatRepository, AppUserRepository appUserRepository) {
        this.chatRepository = chatRepository;
        this.appUserRepository = appUserRepository;
    }

    public Chat createChat(Chat chat, List<Long> participantIds) {
        Chat savedChat = chatRepository.save(chat);

        participantIds.forEach(userId -> {
            AppUser user = appUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserChat userChat = new UserChat();
            userChat.setUser(user);
            userChat.setChat(savedChat);
            savedChat.addParticipant(userChat);
        });

        return chatRepository.save(savedChat);
    }

    public List<Chat> getAllChats(Long userId) {
        return chatRepository.findByParticipantsUserId(userId);
    }

    public Chat updateChat(Long chatId, Chat updatedChat, Set<UserChat> participants) {
        Chat existingChat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        existingChat.setGroup(updatedChat.getGroup());
        existingChat.setGroupName(updatedChat.getGroupName());
        existingChat.setParticipants(participants);
        return chatRepository.save(existingChat);
    }

    public void removeUserFromChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        UserChat participant = chat.getParticipants().stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not part of this chat"));
        chat.removeParticipant(participant);
        chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        if (chatRepository.existsById(chatId)) {
            chatRepository.deleteById(chatId);
        } else {
            throw new RuntimeException("Chat not found");
        }
    }
}