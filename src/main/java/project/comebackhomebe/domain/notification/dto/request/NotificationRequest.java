package project.comebackhomebe.domain.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "알림 보내기 Request")
public record NotificationRequest(
        Long user_id,
        String token,
        String title,
        String message
) {
}
