package project.comebackhomebe.domain.chat.chatMessage.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chat_message")
public class ChatMessage {

    /**
     * 채팅 고유 ID
     */
    @Id
    private String id;

    /**
     * 채팅방 고유 ID
     */
    private String chatId;

    /**
     * 보내는 사람 ID
     */
    private String senderId;

    /**
     * 받는 사람 ID
     */
    private String receiverId;

    /**
     * 내용
     */
    private String content;

    /**
     * 시간
     */
    private LocalDateTime timestamp;

    /**
     * 채팅 메세지 생성 빌더
     * @param chatMessageInfo
     * @param chatId
     * @return
     */
    public static ChatMessage from(ChatMessageInfo chatMessageInfo, String chatId) {
        return ChatMessage.builder()
                .chatId(chatId)
                .senderId(chatMessageInfo.senderId())
                .receiverId(chatMessageInfo.receiverId())
                .content(chatMessageInfo.content())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
