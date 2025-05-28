package project.comebackhomebe.domain.chat.chatRoom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    /**
     * 보낸 사람과 받는 사람 각각의 ID를 받아 채팅방을 찾음
     * @param senderId
     * @param receiverId
     * @return
     */
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);

    /**
     * 보내는 사람의 ID를 가지고 채팅방 리스트를 찾음
     * @param senderId
     * @return
     */
    List<ChatRoom> findBySenderId(String senderId);
}
