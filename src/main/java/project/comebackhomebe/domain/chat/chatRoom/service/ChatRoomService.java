package project.comebackhomebe.domain.chat.chatRoom.service;

import jakarta.servlet.http.HttpServletRequest;
import project.comebackhomebe.domain.chat.chatRoom.dto.ChatRoomInfo;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatRoomId(String senderId, String receiverId);

    Long getSenderId(HttpServletRequest request);

    Long getReceiverId(Long dogId);

    List<ChatRoomInfo> getListChatRoom(HttpServletRequest request);

    ChatRoomInfo createChatRooms(HttpServletRequest request, Long dogId);
}
