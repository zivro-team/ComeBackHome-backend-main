package project.comebackhomebe.domain.chat.chatRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
