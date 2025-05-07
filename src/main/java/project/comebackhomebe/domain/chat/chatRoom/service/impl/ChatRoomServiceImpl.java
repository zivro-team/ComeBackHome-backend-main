package project.comebackhomebe.domain.chat.chatRoom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
}
