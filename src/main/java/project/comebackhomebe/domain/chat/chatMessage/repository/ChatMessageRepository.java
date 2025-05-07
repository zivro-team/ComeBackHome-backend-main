package project.comebackhomebe.domain.chat.chatMessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
