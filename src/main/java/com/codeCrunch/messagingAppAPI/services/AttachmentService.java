package com.codeCrunch.messagingAppAPI.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.codeCrunch.messagingAppAPI.models.*;
import com.codeCrunch.messagingAppAPI.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AmazonS3 amazonS3;

    // Application properties
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public AttachmentService(AttachmentRepository attachmentRepository, AmazonS3 amazonS3) {
        this.attachmentRepository = attachmentRepository;
        this.amazonS3 = amazonS3;
    }

    public Attachment uploadAttachment(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        amazonS3.putObject(new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String fileUrl = amazonS3.getUrl(bucketName, uniqueFileName).toString();

        Attachment attachment = new Attachment();
        attachment.setFileUrl(fileUrl);
        attachment.setFileType(determineFileType(file.getContentType()));
        attachment.setFileSize((int) file.getSize());

        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    public void deleteAttachment(Long id) {
        Attachment attachment = getAttachmentById(id);
        String fileKey = attachment.getFileUrl().substring(attachment.getFileUrl().lastIndexOf('/') + 1);

        amazonS3.deleteObject(bucketName, fileKey);

        attachmentRepository.deleteById(id);
    }

    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    private Attachment.FileType determineFileType(String contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content type cannot be null");
        }
        return switch (contentType) {
            case "image/png" -> Attachment.FileType.PNG;
            case "image/jpeg" -> Attachment.FileType.JPEG;
            case "application/pdf" -> Attachment.FileType.PDF;
            case "text/plain" -> Attachment.FileType.TXT;
            default -> throw new IllegalArgumentException("Unsupported file type: " + contentType);
        };
    }
}