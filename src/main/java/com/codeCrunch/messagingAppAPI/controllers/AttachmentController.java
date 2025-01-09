package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.dto.AttachmentDTO;
import com.codeCrunch.messagingAppAPI.dto.MessageDTO;
import com.codeCrunch.messagingAppAPI.models.Attachment;
import com.codeCrunch.messagingAppAPI.models.Message;
import com.codeCrunch.messagingAppAPI.services.AttachmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    /**
     * Upload a file as a new attachment (returns AttachmentDTO).
     */
    @PostMapping
    public ResponseEntity<AttachmentDTO> uploadAttachment(@RequestParam("file") MultipartFile file, @RequestParam("messageId") Long messageId)
            throws IOException {
        Attachment uploaded = attachmentService.uploadAttachment(file, messageId);
        return ResponseEntity.ok(mapToDTO(uploaded));
    }

    /**
     * Get a single attachment by ID (returns AttachmentDTO).
     */
    @GetMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        return ResponseEntity.ok(mapToDTO(attachment));
    }

    /**
     * Delete an attachment by ID (returns a string message).
     */
    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.ok("Attachment deleted successfully");
    }

    /**
     * Get all attachments (returns List of DTOs).
     */
    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments() {
        List<Attachment> attachments = attachmentService.getAllAttachments();
        List<AttachmentDTO> dtos = attachments.stream()
                .map(this::mapToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get the message associated with a specific attachment.
     */
    @GetMapping("/{attachmentId}/message")
    public ResponseEntity<MessageDTO> getMessageForAttachment(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        Message message = attachment.getMessage();

        if (message == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no message is linked
        }
        MessageDTO messageDTO = mapMessageToDTO(message);

        return ResponseEntity.ok(messageDTO);
    }

    // ============= MAPPING UTILITIES =============

    private AttachmentDTO mapToDTO(Attachment attachment) {
        AttachmentDTO dto = new AttachmentDTO();
        dto.setFileUrl(attachment.getFileUrl());
        dto.setFileType(attachment.getFileType().name());
        dto.setFileSize(attachment.getFileSize());
        dto.setUploadedAt(attachment.getUploadedAt());
        return dto;
    }
    private MessageDTO mapMessageToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        if (message.getChat() != null) {
            dto.setChatId(message.getChat().getId());
        }
        if (message.getSender() != null) {
            dto.setSenderId(message.getSender().getId());
        }
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        dto.setStatus(message.getStatus().name());
        return dto;
    }

}
