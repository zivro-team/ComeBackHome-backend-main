package project.comebackhomebe.domain.chat.chatMessage.dto;

import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;
import java.util.stream.Collectors;

public record ChatMessageInfo(
        String senderId,
        String receiverId,
        String content
) {
    public static ChatMessageInfo of(ChatMessage chatMessage) {
        return new ChatMessageInfo(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                chatMessage.getContent());
    }

    public static List<ChatMessageInfo> listOf(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(ChatMessageInfo::of)
                .collect(Collectors.toList());
    }
}
