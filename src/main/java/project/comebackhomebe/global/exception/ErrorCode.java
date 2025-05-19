package project.comebackhomebe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // 테스트
    TEST(00, "테스트입니다.", HttpStatus.OK),

    // 공용에러
    INTERNAL_SERVER_ERROR(1000, "서버에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_INPUT_ERROR(1001, "유효하지 않은 값입니다.", HttpStatus.BAD_REQUEST),

    // 사용자
    ACCESS_TOKEN_NOT_FOUND(2000, "액세스 토큰을 보유하고 있지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_USER_VERIFICATION(2001, "사용자의 계정이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(2002, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_USER_ACCOUNT(2003, "유효하지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(2004, "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_SOCIAL_ID(2005, "잘못된 소셜 접근입니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_FOUND(2006, "리프레쉬 토큰이 없습니다.", HttpStatus.UNAUTHORIZED),

    // 강아지 정보
    DOG_NOT_FOUND(3000, "해당 강아지 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 알림
    NOTIFICATION_NOT_SEND(4000, "알림이 전송되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 채팅
    CHAT_NOT_SAVED(5000, "채팅이 저장되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CHAT_NOT_SENT(5001, "채팅이 보내지지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 추가
    NOT_VERIFY_INFO(9000, "정의되지 않는 에러 발생입니다.", HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
