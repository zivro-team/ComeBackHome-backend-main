package project.comebackhomebe.domain.chat.chatMessage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.comebackhomebe.domain.chat.chatMessage.dto.request.ChatMessageRequest;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;
import project.comebackhomebe.global.util.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writerName;

    private String content;

    @ManyToOne
    private ChatRoom chatRoom;

    public static ChatMessage from (ChatMessageRequest request, ChatRoom chatRoom) {
        return ChatMessage.builder()
                .writerName(request.writerName())
                .content(request.content())
                .chatRoom(chatRoom)
                .build();
    }

}
