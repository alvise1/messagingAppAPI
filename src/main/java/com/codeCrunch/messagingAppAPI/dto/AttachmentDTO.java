package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;

public class AttachmentDTO {
    private String fileUrl;
    private String fileType;
    private Integer fileSize;
    private LocalDateTime uploadedAt;

    public AttachmentDTO() {
    }

    public AttachmentDTO(String fileUrl, String fileType,
                         Integer fileSize, LocalDateTime uploadedAt) {

        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
