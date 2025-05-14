package project.comebackhomebe.domain.chat.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.comebackhomebe.domain.chat.chatMessage.entity.ChatMessage;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;
import project.comebackhomebe.domain.chat.chatNotification.ChatNotification;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    @SendTo
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage saveMsg = chatMessageService.save(chatMessage);
        // john/queue/
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId(),
                "/queue/messages",
                ChatNotification.builder()
                        .id(saveMsg.getId())
                        .senderId(saveMsg.getSenderId())
                        .receiverId(saveMsg.getReceiverId())
                        .content(saveMsg.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{reciverId}")
    public ResponseEntity<List<ChatMessage>> findChatMessage(
            @PathVariable("senderId") String senderId,
            @PathVariable("reciverId") String reciverId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, reciverId));
    }
}
