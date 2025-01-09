package com.codeCrunch.messagingAppAPI.controllers;

import com.codeCrunch.messagingAppAPI.models.Attachment;
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

    @PostMapping
    public ResponseEntity<Attachment> uploadAttachment(@RequestParam("file") MultipartFile file) throws IOException {
        Attachment uploadedAttachment = attachmentService.uploadAttachment(file);
        return ResponseEntity.ok(uploadedAttachment);
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        return ResponseEntity.ok(attachment);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.ok("Attachment deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<Attachment>> getAllAttachments() {
        List<Attachment> attachments = attachmentService.getAllAttachments();
        return ResponseEntity.ok(attachments);
    }
}
