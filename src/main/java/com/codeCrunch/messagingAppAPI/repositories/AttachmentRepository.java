package com.codeCrunch.messagingAppAPI.repositories;

import com.codeCrunch.messagingAppAPI.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
