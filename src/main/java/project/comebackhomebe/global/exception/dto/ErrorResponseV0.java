package project.comebackhomebe.global.exception.dto;

public record ErrorResponseV0(
        String name,
        int errorCode,
        String message
) {
}
