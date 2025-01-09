package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.Chat;
import com.codeCrunch.messagingAppAPI.models.UserChat;
import com.codeCrunch.messagingAppAPI.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat, @RequestBody List<Long> participantIds) {
        Chat createdChat = chatService.createChat(chat, participantIds);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats(@RequestParam Long userId) {
        List<Chat> chats = chatService.getAllChats(userId);
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<Chat> updateChat(@PathVariable Long chatId, @RequestBody Chat chat, @RequestBody Set<UserChat> participants) {
        Chat updatedChat = chatService.updateChat(chatId, chat, participants);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }

    @DeleteMapping("/{chatId}/users/{userId}")
    public ResponseEntity<String> removeUserFromChat(@PathVariable Long chatId, @PathVariable Long userId) {
        chatService.removeUserFromChat(chatId, userId);
        return ResponseEntity.ok("User removed from chat successfully.");
    }

}