package project.comebackhomebe.domain.chat.chatMessage.service;

import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;

import java.util.List;

public interface ChatMessageService {
    ChatMessageInfo save(ChatMessageInfo chatMessageinfo);

    List<ChatMessageInfo> findChatMessage(String senderId, String receiverId);

    void processMessage(ChatMessageInfo chatMessageinfo);
}
