package project.comebackhomebe.domain.chat.chatMessage.service;

import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findChatMessage(String senderId, String receiverId);
}
