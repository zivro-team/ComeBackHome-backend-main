package project.comebackhomebe.global.security.exception;

import lombok.RequiredArgsConstructor;
import okhttp3.internal.http2.ErrorCode;

@RequiredArgsConstructor
public class OAuth2Exception extends RuntimeException {

    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
