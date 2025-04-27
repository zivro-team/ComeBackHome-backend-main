package project.comebackhomebe.global.firebase.dto;

public record FCMTokenResponse(
        String token
) {
    public static FCMTokenResponse of(String token) {
        return new FCMTokenResponse(token);
    }
}
