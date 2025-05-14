package project.comebackhomebe.domain.chat.chatRoom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;
import project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists) {

        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        String chatId = createChatId(senderId, receiverId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String receiverId) {
        String chatId = String.format(senderId, receiverId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
