package project.comebackhomebe.domain.chat.chatMessage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String s);
}
