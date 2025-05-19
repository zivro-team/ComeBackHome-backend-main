package project.comebackhomebe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // 테스트
    TEST(00, "테스트입니다."),

    // 공용에러
    INTERNAL_SERVER_ERROR(1000, "서버에 오류가 발생했습니다."),
    INTERNAL_INPUT_ERROR(1001, "유효하지 않은 값입니다."),

    // 사용자
    ACCESS_TOKEN_NOT_FOUND(2000, "액세스 토큰을 보유하고 있지 않습니다."),
    INVALID_USER_VERIFICATION(2001, "사용자의 계정이 유효하지 않습니다."),
    USER_NOT_FOUND(2002, "사용자를 찾을 수 없습니다."),
    INVALID_USER_ACCOUNT(2003, "유효하지 않은 사용자입니다."),
    TOKEN_EXPIRED(2004, "만료된 토큰입니다."),
    INVALID_SOCIAL_ID(2005, "잘못된 소셜 접근입니다."),
    REFRESH_TOKEN_NOT_FOUND(2006, "리프레쉬 토큰이 없습니다."),

    // 강아지 정보
    DOG_NOT_FOUND(3000, "해당 강아지 정보를 찾을 수 없습니다."),

    // 알림
    NOTIFICATION_NOT_SEND(4000, "알림이 전송되지 않았습니다."),

    // 채팅
    CHAT_NOT_SAVED(5000, "채팅이 저장되지 않았습니다."),
    CHAT_NOT_SENT(5001, "채팅이 보내지지 않았습니다."),

    // 추가
    NOT_VERIFY_INFO(9000, "정의되지 않는 에러 발생입니다.");

    private final int errorCode;
    private final String message;
}
