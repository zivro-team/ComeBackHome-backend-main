package project.comebackhomebe.domain.chat.chatRoom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatRoom.dto.response.ChatRoomResponse;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;
import project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom make(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    @Override
    public List<ChatRoom> getList() {
        return chatRoomRepository.findAll();
    }

    @Override
    public ChatRoomResponse findById(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);

        return ChatRoomResponse.of(chatRoom);
    }


}
