package project.comebackhomebe.domain.chat.chatMessage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;
import project.comebackhomebe.domain.chat.chatMessage.repository.ChatMessageRepository;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                true)
                .orElseThrow();
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;

    }

    @Override
    public List<ChatMessage> findChatMessage(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                receiverId,
                false);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }
}
