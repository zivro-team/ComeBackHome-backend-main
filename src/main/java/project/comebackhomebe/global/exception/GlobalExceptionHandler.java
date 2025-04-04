package project.comebackhomebe.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.global.exception.dto.ErrorResponseV0;
import project.comebackhomebe.global.security.exception.OAuth2Exception;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // OAuth2 관련 에러 핸들링
    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<ErrorResponseV0> handleOAuth2Exception(OAuth2Exception exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }

    // 멤버 관련 에러 핸들링
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponseV0> handleMemberException(MemberException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.ok(ErrorResponseV0.of(errorCode));
    }


}