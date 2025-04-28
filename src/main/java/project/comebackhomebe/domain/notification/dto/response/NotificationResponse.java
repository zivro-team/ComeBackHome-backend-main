package project.comebackhomebe.domain.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.notification.entity.Notification;

@Schema(title = "알림 응답 Response")
public record NotificationResponse(
        String message
) {

    public static NotificationResponse of(Notification notification) {
        return new NotificationResponse(notification.getMessage());
    }

}
