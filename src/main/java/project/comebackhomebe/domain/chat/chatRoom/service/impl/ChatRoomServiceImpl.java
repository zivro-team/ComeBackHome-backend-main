package project.comebackhomebe.domain.chat.chatRoom.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.chat.chatRoom.dto.ChatRoomInfo;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;
import project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.jwt.JwtService;
import project.comebackhomebe.global.security.jwt.JwtUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;

    @Override
    public List<ChatRoomInfo> getListChatRoom(HttpServletRequest request) {
        String senderId = getSenderId(request).toString();
        log.info("[getListChatRoom] senderId: {}", senderId);

        List<ChatRoom> chatRooms = chatRoomRepository.findBySenderId(senderId);
        log.info("[getListChatRoom] chatRooms: {}", chatRooms);

        return ChatRoomInfo.listOf(chatRooms);
    }

    @Override
    public Optional<String> getChatRoomId(String senderId, String receiverId) {
        return getChatRoom(senderId, receiverId)
                .map(ChatRoom::getChatId);
    }

    private Optional<ChatRoom> getChatRoom(String senderId, String receiverId) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public ChatRoomInfo createChatRooms(HttpServletRequest request, Long dogId) {
        String senderId = getSenderId(request).toString();
        log.info("[createChatRoom] senderId : " + senderId);

        String receiverId = getReceiverId(dogId).toString();
        log.info("[createChatRoom] receiverId : " + receiverId);

        var chatId = String.format("%s%s", senderId, receiverId); // 공용으로 사용되는 chatRoomID
        log.info("[createChatRoom] chatId : " + chatId);

        ChatRoom senderRecipient = ChatRoom.from(chatId, senderId, receiverId); // 채팅방에 고유값이야
        chatRoomRepository.save(senderRecipient);
        log.info("[createChatRoom] senderRoom : " + senderRecipient);

        ChatRoom recipientSender = ChatRoom.from(chatId, receiverId, senderId);
        chatRoomRepository.save(recipientSender);
        log.info("[createChatRoom] recipientRoom : " + recipientSender);

        return ChatRoomInfo.of(senderRecipient);
    }

    @Override
    public Long getSenderId(HttpServletRequest request) {
        return memberRepository.findIdByVerifyKey(
                jwtUtil.getVerifyKey(
                        jwtService.resolveAccessToken(request)
                )
        );
    }

    @Override
    public Long getReceiverId(Long dogId) {
        return dogRepository.findMemberIdByDogId(dogId);
    }
}
