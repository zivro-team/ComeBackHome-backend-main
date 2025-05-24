package project.comebackhomebe.domain.chat.chatRoom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<ChatRoom> findBySenderId(String senderId);
}
