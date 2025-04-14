package project.comebackhomebe.domain.notification.dto.request;

public record NotificationRequest(
        Long user_id,
        String token,
        String title,
        String message
) {
}
