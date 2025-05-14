package project.comebackhomebe.domain.chat.chatRoom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ChatRoom {

    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String receiverId;
}
