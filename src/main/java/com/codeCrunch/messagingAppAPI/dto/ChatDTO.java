package com.codeCrunch.messagingAppAPI.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class ChatDTO {
    private Long id;
    private boolean isGroup;
    private String groupName;
    private LocalDateTime createdAt;
    private Long lastMessageId;
    private Set<Long> participantIds;

    public ChatDTO() {
    }

    public ChatDTO(
        Long id,
        boolean isGroup,
        String groupName,
        LocalDateTime createdAt,
        Long lastMessageId,
        Set<Long> participantIds
    ) {
       this.id = id;
       this.isGroup = isGroup;
       this.groupName = groupName;
       this.createdAt = createdAt;
       this.lastMessageId = lastMessageId;
       this.participantIds = participantIds;
    }

    public Set<Long> getParticipantIds() {
        return participantIds;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setLastMessageId(Long id) {
        this.lastMessageId = id;
    }

    public void setParticipantIds(Set<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public boolean getGroup() {
        return isGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
