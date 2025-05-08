package project.comebackhomebe.domain.chat.chatRoom.service;

import project.comebackhomebe.domain.chat.chatRoom.dto.response.ChatRoomResponse;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom make(String name);

    List<ChatRoom> getList();

    ChatRoomResponse findById(Long id);
}
