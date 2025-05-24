package project.comebackhomebe.domain.chat.chatRoom.dto;

import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

import java.util.List;
import java.util.stream.Collectors;

public record ChatRoomInfo(
        String senderId,
        String receiverId,
        String chatRoomId
) {
    public static ChatRoomInfo of(ChatRoom chatRoom) {
        return new ChatRoomInfo(
                chatRoom.getSenderId(),
                chatRoom.getReceiverId(),
                chatRoom.getChatId()
        );
    }

    public static List<ChatRoomInfo> listOf(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
                .map(ChatRoomInfo::of)
                .collect(Collectors.toList());
    }
}
