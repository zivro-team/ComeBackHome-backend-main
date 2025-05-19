package project.comebackhomebe.domain.chat.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageInfo chatMessageinfo) {
        chatMessageService.processMessage(chatMessageinfo);
    }

    /**
     * 채팅방 들어가기
     * 처음에만 들어가면 됨
     */
    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessageInfo>> findChatMessage(
            @PathVariable("senderId") String senderId,
            @PathVariable("receiverId") String receiverId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, receiverId));
    }
}
