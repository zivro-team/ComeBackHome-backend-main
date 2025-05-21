package project.comebackhomebe.domain.chat.chatMessage.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collation = "chat_message")
public class ChatMessage {

    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String receiverId;

    private String content;

    private Date timestamp;

    public static ChatMessage from(ChatMessageInfo chatMessageInfo, String chatId) {
        return ChatMessage.builder()
                .chatId(chatId)
                .senderId(chatMessageInfo.senderId())
                .receiverId(chatMessageInfo.receiverId())
                .content(chatMessageInfo.content())
                .build();
    }
}
