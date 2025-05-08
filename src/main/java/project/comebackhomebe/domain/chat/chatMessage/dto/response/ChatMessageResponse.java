package project.comebackhomebe.domain.chat.chatMessage.dto.response;

import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;
import java.util.stream.Collectors;

public record ChatMessageResponse(
        String writerName,
        String content
) {
    public static ChatMessageResponse of(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getWriterName(),
                chatMessage.getContent()
        );
    }

    public static List<ChatMessageResponse> listOf(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(ChatMessageResponse::of)
                .collect(Collectors.toList());
    }
}
