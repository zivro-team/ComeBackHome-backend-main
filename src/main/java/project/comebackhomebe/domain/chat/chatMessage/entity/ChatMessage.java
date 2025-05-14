package project.comebackhomebe.domain.chat.chatMessage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ChatMessage {

    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String receiverId;

    private String content;

    private Date timestamp;
}
