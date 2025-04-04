package project.comebackhomebe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // 테스트
    TEST(00, "테스트입니다."),

    // 사용자
    NOT_HAVE_ACCESSTOKEN(00, "액세스 토큰을 보유하고 있지 않습니다."),

    // OAuth2
    NOT_VERIFY_INFO(00, "사용자의 계정이 유효하지 않습니다.");

    private final int errorCode;
    private final String message;
}
