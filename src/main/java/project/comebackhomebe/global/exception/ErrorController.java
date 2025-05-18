package project.comebackhomebe.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.global.exception.dto.ErrorResponseV0;

@RestController
public class ErrorController {

    @GetMapping("/access-denied")
    public ResponseEntity<ErrorResponseV0> accessDenied() {
        // 접근 거부에 대한 ErrorCode를 추가하거나 기존 코드 사용
        ErrorCode errorCode = ErrorCode.NOT_VERIFY_INFO; // 또는 새로운 ACCESS_DENIED 에러 코드 생성

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseV0.of(errorCode));
    }
}
