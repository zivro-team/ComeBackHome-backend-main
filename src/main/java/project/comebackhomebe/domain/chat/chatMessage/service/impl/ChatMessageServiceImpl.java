package project.comebackhomebe.domain.chat.chatMessage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatMessage.repository.ChatMessageRepository;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
}
