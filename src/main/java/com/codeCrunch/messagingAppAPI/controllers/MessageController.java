package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.MessageDTO;
import com.codeCrunch.messagingAppAPI.models.Message;
import com.codeCrunch.messagingAppAPI.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Send a new message.
     */
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        // Convert DTO -> Entity
        Message messageEntity = mapToEntity(messageDTO);

        // Call service
        Message savedMessage = messageService.sendMessage(messageEntity);

        // Convert Entity -> DTO
        MessageDTO resultDTO = mapToDTO(savedMessage);
        return ResponseEntity.ok(resultDTO);
    }

    /**
     * Get messages for a given chat ID, as a list of MessageDTO.
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        // Convert each Message -> MessageDTO
        List<MessageDTO> messageDTOs = messages.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(messageDTOs);
    }

    /**
     * Delete a message by its ID.
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    /**
     * Update a message's status (e.g., "DELIVERED" or "READ").
     */
    @PatchMapping("/{messageId}/status")
    public ResponseEntity<MessageDTO> updateMessageStatus(
            @PathVariable Long messageId,
            @RequestBody MessageDTO messageDTO
    ) {
        Message.MessageStatus newStatus =
                Message.MessageStatus.valueOf(messageDTO.getStatus());

        Message updatedMessage = messageService.updateMessageStatus(messageId, newStatus);

        return ResponseEntity.ok(mapToDTO(updatedMessage));
    }

    // ================== MAPPING UTILS ===================

    private MessageDTO mapToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        if (message.getChat() != null) {
            dto.setChatId(message.getChat().getId());
        }
        if (message.getSender() != null) {
            dto.setSenderId(message.getSender().getId());
        }
        dto.setContent(message.getContent());
        dto.setStatus(message.getStatus().name());
        return dto;
    }

    private Message mapToEntity(MessageDTO dto) {
        Message message = new Message();

        message.setContent(dto.getContent());
        if (dto.getStatus() != null) {
            message.setStatus(Message.MessageStatus.valueOf(dto.getStatus()));
        }

        return message;
    }
}
