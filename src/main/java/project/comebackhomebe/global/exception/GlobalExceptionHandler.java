package project.comebackhomebe.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.comebackhomebe.domain.chat.chatMessage.exception.ChatException;
import project.comebackhomebe.domain.dog.dogInfo.exception.DogException;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.domain.notification.exception.NotificationException;
import project.comebackhomebe.global.exception.dto.ErrorResponseV0;
import project.comebackhomebe.global.security.exception.OAuth2Exception;
import project.comebackhomebe.global.slack.service.SlackService;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackService slackService;

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<ErrorResponseV0> handleOAuth2Exception(OAuth2Exception exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        slackService.sendSlackMessage(exception, errorCode, request);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponseV0> handleMemberException(MemberException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        slackService.sendSlackMessage(exception, errorCode, request);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(DogException.class)
    public ResponseEntity<ErrorResponseV0> handleDogInfoException(DogException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        slackService.sendSlackMessage(exception, errorCode, request);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponseV0> handleNotificationException(NotificationException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        slackService.sendSlackMessage(exception, errorCode, request);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseV0.of(errorCode));
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorResponseV0> handleChatException(ChatException exception, HttpServletRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        slackService.sendSlackMessage(exception, errorCode, request);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseV0.of(errorCode));
    }
}