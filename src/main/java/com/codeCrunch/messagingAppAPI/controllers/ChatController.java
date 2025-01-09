package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.Chat;
import com.codeCrunch.messagingAppAPI.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats(@RequestParam Long userId) {
        List<Chat> chats = chatService.getAllChats(userId);
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/{chatId}")
    public ResponseEntity<Chat> updateChat(@PathVariable Long chatId, @RequestBody Chat chat) {
        Chat updatedChat = chatService.updateChat(chatId, chat);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }
}