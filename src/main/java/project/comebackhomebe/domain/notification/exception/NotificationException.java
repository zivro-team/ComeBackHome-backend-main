package project.comebackhomebe.domain.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.comebackhomebe.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public class NotificationException extends RuntimeException {
    private final ErrorCode errorCode;
}
