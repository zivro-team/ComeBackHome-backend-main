package project.comebackhomebe.global.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.comebackhomebe.global.exception.ErrorCode;


@RequiredArgsConstructor
@Getter
public class OAuth2Exception extends RuntimeException {

    private final ErrorCode errorCode;

}
