package project.comebackhomebe.domain.chat.chatMessage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.chat.chatMessage.dto.ChatMessageInfo;
import project.comebackhomebe.domain.chat.chatMessage.service.ChatMessageService;
import project.comebackhomebe.domain.chat.chatRoom.dto.ChatRoomInfo;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 들어가기
     *
     * @param senderId
     * @param receiverId
     * @return
     */
    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessageInfo>> findChatMessage(
            @PathVariable("senderId") String senderId,
            @PathVariable("receiverId") String receiverId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, receiverId));
    }

    /**
     * 처음 채팅을 시작하고 싶을 때 사용하는 API
     * 액세스 토큰과 dog 에서 두개의 id를 추출하여 채팅방 만듬
     *
     * @param request : 액세스 토큰
     * @param dogId   : 게시글 Dog ID
     * @return
     */
    @PostMapping("/chatRoom")
    public ResponseEntity<ChatRoomInfo> findChatMessageSender(
            HttpServletRequest request,
            @RequestBody Long dogId
    ) {
        return ResponseEntity.ok(chatRoomService.createChatRooms(request, dogId));
    }

    /**
     * 액세스 토큰으로 senderId로 변환하여
     * 채팅방 리스트 보이게 만듬
     *
     * @param request : 액세스 토큰
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomInfo>> findChatRoom(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(chatRoomService.getListChatRoom(request));
    }


}
