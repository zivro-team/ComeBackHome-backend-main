package project.comebackhomebe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    TEST(00, "테스트입니다.");






    private final int errorCode;
    private final String message;
}
