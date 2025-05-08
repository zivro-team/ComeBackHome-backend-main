package project.comebackhomebe.domain.chat.chatMessage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatMessage.dto.request.ChatMessageRequest;
import project.comebackhomebe.domain.chat.chatMessage.dto.response.ChatMessageResponse;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;
import project.comebackhomebe.domain.chat.chatMessage.repository.ChatMessageRepository;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageResponse write(ChatMessageRequest request, ChatRoom chatRoom) {
        ChatMessage chatMessage = ChatMessage.from(request,chatRoom);

        chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.of(chatMessage);
    }
}
