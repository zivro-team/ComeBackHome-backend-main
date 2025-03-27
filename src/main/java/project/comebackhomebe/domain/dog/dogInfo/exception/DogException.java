package project.comebackhomebe.domain.dog.dogInfo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.comebackhomebe.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public class DogException extends RuntimeException {
    private final ErrorCode errorCode;
}
