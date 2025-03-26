package project.comebackhomebe.global.exception.dto;

import lombok.Builder;
import project.comebackhomebe.global.exception.ErrorCode;

@Builder
public record ErrorResponseV0(
        String name,
        int errorCode,
        String message
) {

    // 엔티티 -> DTO
    public static ErrorResponseV0 of(ErrorCode errorCode) {
        return ErrorResponseV0.builder()
                .name(errorCode.name())
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }
}
