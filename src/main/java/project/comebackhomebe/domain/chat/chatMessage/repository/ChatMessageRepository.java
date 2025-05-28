package project.comebackhomebe.domain.chat.chatMessage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    /**
     * 채팅방 ID로 채팅내역 불러오기
     * @param s
     * @return
     */
    List<ChatMessage> findByChatId(String s);
}
