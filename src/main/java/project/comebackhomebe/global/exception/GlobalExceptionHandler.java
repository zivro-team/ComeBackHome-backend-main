package project.comebackhomebe.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.comebackhomebe.domain.chat.chatMessage.exception.ChatException;
import project.comebackhomebe.domain.dog.dogInfo.exception.DogException;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.domain.notification.exception.NotificationException;
import project.comebackhomebe.global.exception.dto.ErrorResponseV0;
import project.comebackhomebe.global.security.exception.OAuth2Exception;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<ErrorResponseV0> handleOAuth2Exception(OAuth2Exception exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponseV0> handleMemberException(MemberException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(DogException.class)
    public ResponseEntity<ErrorResponseV0> handleDogInfoException(DogException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponseV0> handleNotificationException(NotificationException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorResponseV0> handleChatException(ChatException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }


}