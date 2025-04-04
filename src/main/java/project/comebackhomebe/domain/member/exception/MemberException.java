package project.comebackhomebe.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.comebackhomebe.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;
}
