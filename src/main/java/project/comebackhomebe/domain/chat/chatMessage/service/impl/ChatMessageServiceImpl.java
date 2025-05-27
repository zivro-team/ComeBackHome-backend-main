package project.comebackhomebe.domain.chat.chatMessage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j  // 이 어노테이션 추가

public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatMessageInfo save(ChatMessageInfo chatMessageInfo) {
        log.info("=== ChatMessage 저장 시작 ===");
        log.info("입력 데이터: {}", chatMessageInfo);

        var chatId = chatRoomService.getChatRoomId(
                        chatMessageInfo.senderId(),
                        chatMessageInfo.receiverId())
                .orElseThrow(() -> {
                    log.error("ChatRoom을 찾을 수 없습니다. senderId: {}, receiverId: {}",
                            chatMessageInfo.senderId(), chatMessageInfo.receiverId());
                    return new RuntimeException("ChatRoom not found");
                });

        log.info("찾은 chatId: {}", chatId);

        ChatMessage chatMessage = ChatMessage.from(chatMessageInfo, chatId);

        chatMessage.setChatId(chatId);

        chatMessageRepository.save(chatMessage);

        return ChatMessageInfo.of(chatMessage);
    }

    @Override
    public List<ChatMessageInfo> findChatMessage(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                receiverId
        );

        List<ChatMessage> messages = chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());

        return ChatMessageInfo.listOf(messages);
    }

    @Override
    public void processMessage(ChatMessageInfo chatMessageinfo) {
        log.info("=== processMessage 시작 ===");
        log.info("처리할 메시지: {}", chatMessageinfo);

        try {
            ChatMessageInfo saveMsg = save(chatMessageinfo);
            log.info("저장 성공: {}", saveMsg);

//            messagingTemplate.convertAndSendToUser(
//                    chatMessageinfo.receiverId(),
//                    "/queue/messages",
//                    saveMsg
//            );

            messagingTemplate.convertAndSend(
                    "/topic/chatMessage/"
                            + chatMessageinfo.senderId()
                            + chatMessageinfo.receiverId(),
                    saveMsg);

            log.info("메시지 전송 완료");
        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생", e);
            throw e;
        }
        log.info("=== processMessage 완료 ===");
    }
}
