package project.comebackhomebe.domain.chat.chatMessage.service;

import project.comebackhomebe.domain.chat.chatMessage.dto.request.ChatMessageRequest;
import project.comebackhomebe.domain.chat.chatMessage.dto.response.ChatMessageResponse;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

public interface ChatMessageService {
    ChatMessageResponse write(ChatMessageRequest request, ChatRoom chatRoom);
}
