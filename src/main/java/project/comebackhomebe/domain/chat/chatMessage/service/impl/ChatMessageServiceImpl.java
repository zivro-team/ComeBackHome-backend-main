package project.comebackhomebe.domain.chat.chatMessage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;
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
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatMessageInfo save(ChatMessageInfo chatMessageInfo) {
        var chatId = chatRoomService.getChatRoomId(
                        chatMessageInfo.senderId(),
                        chatMessageInfo.receiverId(),
                        true)
                .orElseThrow();

        ChatMessage chatMessage = ChatMessage.from(chatMessageInfo, chatId);

        chatMessage.setChatId(chatId);

        chatMessageRepository.save(chatMessage);

        return ChatMessageInfo.of(chatMessage);
    }

    @Override
    public List<ChatMessageInfo> findChatMessage(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                receiverId,
                false);

        List<ChatMessage> messages = chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());

        return ChatMessageInfo.listOf(messages);
    }

    @Override
    public void processMessage(ChatMessageInfo chatMessageinfo) {
        ChatMessageInfo saveMsg = save(chatMessageinfo);

        messagingTemplate.convertAndSendToUser(
                chatMessageinfo.receiverId(),
                "/queue/messages",
                saveMsg
        );
    }
}
