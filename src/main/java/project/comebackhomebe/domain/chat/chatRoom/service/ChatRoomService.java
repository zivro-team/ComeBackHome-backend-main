package project.comebackhomebe.domain.chat.chatRoom.service;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists);
}
