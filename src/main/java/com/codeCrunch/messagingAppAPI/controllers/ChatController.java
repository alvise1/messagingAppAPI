package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.ChatDTO;
import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.models.Chat;
import com.codeCrunch.messagingAppAPI.models.UserChat;
import com.codeCrunch.messagingAppAPI.repositories.AppUserRepository;
import com.codeCrunch.messagingAppAPI.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;
    private final AppUserRepository appUserRepository;

    public ChatController(ChatService chatService, AppUserRepository appUserRepository) {
        this.chatService = chatService;
        this.appUserRepository = appUserRepository;
    }

    /**
     * Create a new chat (group or direct) with participant IDs.
     */
    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
        // Map DTO -> Entity
        Chat chatEntity = mapToEntity(chatDTO);

        // Suppose we pass participant IDs in chatDTO.getParticipantIds().
        List<Long> participantIds = chatDTO.getParticipantIds() != null
                ? new ArrayList<>(chatDTO.getParticipantIds()) : new ArrayList<>();

        Chat savedChat = chatService.createChat(chatEntity, participantIds);
        return ResponseEntity.ok(mapToDTO(savedChat));
    }

    /**
     * Get all chats for a user (by userId).
     */
    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChats(@RequestParam Long userId) {
        List<Chat> chats = chatService.getAllChats(userId);
        List<ChatDTO> dtos = chats.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Update a chat (e.g. group name). We also can update participants if needed.
     */
    @PutMapping("/{chatId}")
    public ResponseEntity<ChatDTO> updateChat(@PathVariable Long chatId, @RequestBody ChatDTO chatDTO) {
        Chat updatedChatEntity = mapToEntity(chatDTO);

        // If the DTO includes participant IDs, we convert them to UserChat objects:
        Set<UserChat> participants = new HashSet<>();
        if (chatDTO.getParticipantIds() != null) {
            for (Long userId : chatDTO.getParticipantIds()) {
                AppUser user = appUserRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                UserChat userChat = new UserChat();
                userChat.setUser(user);
                userChat.setChat(updatedChatEntity);
                participants.add(userChat);
            }
        }

        Chat savedChat = chatService.updateChat(chatId, updatedChatEntity, participants);
        return ResponseEntity.ok(mapToDTO(savedChat));
    }

    /**
     * Remove a user from a chat.
     */
    @DeleteMapping("/{chatId}/users/{userId}")
    public ResponseEntity<String> removeUserFromChat(@PathVariable Long chatId, @PathVariable Long userId) {
        chatService.removeUserFromChat(chatId, userId);
        return ResponseEntity.ok("User removed from chat");
    }

    /**
     * Delete an entire chat.
     */
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }

    // =============== Mapping Methods ===============

    private ChatDTO mapToDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setGroup(chat.getGroup());
        dto.setGroupName(chat.getGroupName());
        dto.setCreatedAt(chat.getCreatedAt());

        if (chat.getLastMessage() != null) {
            dto.setLastMessageId(chat.getLastMessage().getId());
        }

        Set<Long> participantIds = new HashSet<>();
        chat.getParticipants().forEach(userChat -> {
            participantIds.add(userChat.getUser().getId());
        });
        dto.setParticipantIds(participantIds);

        return dto;
    }

    private Chat mapToEntity(ChatDTO dto) {
        Chat chat = new Chat();
        chat.setGroup(dto.getGroup());
        chat.setGroupName(dto.getGroupName());
        return chat;
    }
}
