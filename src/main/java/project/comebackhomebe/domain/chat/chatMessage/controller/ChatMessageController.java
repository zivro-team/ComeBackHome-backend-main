package project.comebackhomebe.domain.chat.chatMessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
}
