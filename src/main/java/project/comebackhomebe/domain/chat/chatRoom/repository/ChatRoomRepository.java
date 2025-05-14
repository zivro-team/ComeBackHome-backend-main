package project.comebackhomebe.domain.chat.chatRoom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String receiverId);
}
