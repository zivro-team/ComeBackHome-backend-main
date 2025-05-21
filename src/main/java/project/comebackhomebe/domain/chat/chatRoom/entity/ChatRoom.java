package project.comebackhomebe.domain.chat.chatRoom.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ChatRoom {

    @Id
    private String id;

    private String chatId; // 고유 ChatRoom ID

    private String senderId;

    private String receiverId;

    public static ChatRoom from(String chatId, String senderId, String receiverId) {
        return ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }
}
