package project.comebackhomebe.domain.chat.chatMessage.dto.request;

public record ChatMessageRequest(
        String writerName,
        String content
) {
}
