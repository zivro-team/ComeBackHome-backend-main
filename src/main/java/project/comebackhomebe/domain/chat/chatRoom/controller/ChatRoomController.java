package project.comebackhomebe.domain.chat.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.chat.chatRoom.entity.ChatRoom;
import project.comebackhomebe.domain.chat.chatRoom.service.ChatRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/{roomId}")
    @ResponseBody
    public String showRoom(@PathVariable long roomId,
                           @RequestParam(defaultValue = "NoName") String writerName) {

        return String.format("%d 번 채팅방 입니다. writer : %s", roomId, writerName);
    }

    @GetMapping("/make")
    public String makeRoom() {
        return "domain/chat/chatRoom/make";
    }

    @GetMapping("/list")
    public String roomList(Model model) {
        List<ChatRoom> chatRooms = chatRoomService.getList();

        model.addAttribute("chatRooms", chatRooms);
        return "domain/chat/chatRoom/list";
    }

}
