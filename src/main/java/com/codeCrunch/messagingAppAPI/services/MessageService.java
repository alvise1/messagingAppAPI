package com.codeCrunch.messagingAppAPI.services;

import com.codeCrunch.messagingAppAPI.models.*;
import com.codeCrunch.messagingAppAPI.repositories.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final AppUserRepository appUserRepository;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, AppUserRepository appUserRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.appUserRepository = appUserRepository;
    }

    public Message sendMessage(Message message) {

        if (message.getChat() == null || message.getChat().getId() == null) {
            throw new IllegalArgumentException("Chat information is missing.");
        }
        Chat chat = chatRepository.findById(message.getChat().getId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (message.getSender() == null || message.getSender().getId() == null) {
            throw new IllegalArgumentException("Sender information is missing.");
        }
        AppUser sender = appUserRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty.");
        }

        message.setChat(chat);
        message.setSender(sender);

        if (message.getAttachment() != null) {
            Attachment attachment = message.getAttachment();
            attachment.setMessage(message); // Ensure bidirectional relationship
            message.setAttachment(attachment);
        }

        message.setStatus(Message.MessageStatus.SENT);

        Message savedMessage = messageRepository.save(message);

        chat.setLastMessage(savedMessage);
        chatRepository.save(chat);

        return savedMessage;
    }


    public List<Message> getMessagesByChatId(Long chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new RuntimeException("Chat not found");
        }

        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }

    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // Remove reference from chat's last message if applicable
        Chat chat = message.getChat();
        if (chat.getLastMessage() != null && chat.getLastMessage().getId().equals(messageId)) {
            chat.setLastMessage(null);
            chatRepository.save(chat);
        }

        messageRepository.deleteById(messageId);
    }
}
