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
@Document(collection = "chat_room")
public class ChatRoom {

    /**
     * 채팅방 고유 ID
     */
    @Id
    private String id;

    /**
     * 사용자들끼리 사용될 채팅방 ID
     * Why? 비즈니스 로직상 사용자가 사용하는 ID
     */
    private String chatId; // 고유 ChatRoom ID

    /**
     * 보내는 사람 ID
     */
    private String senderId;

    /**
     * 받는 사람 ID
     */
    private String receiverId;

    /**
     * 채팅방 생성 빌더
     * @param chatId : 15
     * @param senderId : 1
     * @param receiverId : 5
     * @return
     */
    public static ChatRoom from(String chatId, String senderId, String receiverId) {
        return ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
    }
}
