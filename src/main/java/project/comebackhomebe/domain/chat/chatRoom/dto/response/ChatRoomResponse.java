package project.comebackhomebe.domain.chat.chatRoom.dto.response;

import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.List;

public record ChatRoomResponse(
        String writerName,
        List<ChatMessage> messages
) {
    public static ChatRoomResponse of(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.getName(), chatRoom.getMessages()
        );
    }
}
