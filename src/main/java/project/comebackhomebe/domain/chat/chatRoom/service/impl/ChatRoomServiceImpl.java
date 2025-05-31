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

    /**
     * 사용자의 액세스토큰을 추출하여 ID를 찾고
     * 채팅방을 List 형식으로 보여줌
     * @param request
     * @return
     */
    @Override
    public List<ChatRoomInfo> getListChatRoom(HttpServletRequest request) {
        String senderId = getSenderId(request).toString();

        List<ChatRoom> chatRooms = chatRoomRepository.findBySenderId(senderId);

        return ChatRoomInfo.listOf(chatRooms);
    }

    /**
     * 보내는 사람과 받는 사람 각각의 ID를 받아
     * chatId로 채팅방을 찾음
     * @param senderId
     * @param receiverId
     * @return 채팅방 ID
     */
    @Override
    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = String.format("%s%s", senderId, receiverId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    /**
     * 채팅방 생성
     * 액세스 토큰에서 사용자 정보를 추출하고
     * 해당 검증키로 사용자 ID를 가져옴
     * 강아지 게시글에서 강아지 ID를 받아온 다음
     * 등록한 사용자의 ID를 가져옴
     * 이후 각 사용자의 맞게 채팅방을 생성
     * @param request
     * @param dogId
     * @return
     */
    @Override
    public ChatRoomInfo createChatRooms(HttpServletRequest request, Long dogId) {
        String senderId = getSenderId(request).toString();

        String receiverId = getReceiverId(dogId).toString();

        var chatId = String.format("%s%s", senderId, receiverId);

        ChatRoom senderRecipient = ChatRoom.from(chatId, senderId, receiverId);
        chatRoomRepository.save(senderRecipient);

        ChatRoom recipientSender = ChatRoom.from(chatId, receiverId, senderId);
        chatRoomRepository.save(recipientSender);

        return ChatRoomInfo.of(senderRecipient);
    }

    /**
     * 액세스 토큰에서 멤버 ID 추출
     * @param request
     * @return
     */
    @Override
    public Long getSenderId(HttpServletRequest request) {
        return memberRepository.findIdByVerifyKey(
                jwtUtil.getVerifyKey(
                        jwtService.resolveAccessToken(request)
                )
        );
    }

    /**
     * 강아지 게시글에서 멤버 ID 추출
     * @param dogId
     * @return
     */
    @Override
    public Long getReceiverId(Long dogId) {
        return dogRepository.findMemberIdByDogId(dogId);
    }
}
