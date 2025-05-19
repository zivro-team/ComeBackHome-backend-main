package project.comebackhomebe.domain.chat.chatMessage.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.comebackhomebe.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public class ChatException extends RuntimeException {
    private final ErrorCode errorCode;
}
