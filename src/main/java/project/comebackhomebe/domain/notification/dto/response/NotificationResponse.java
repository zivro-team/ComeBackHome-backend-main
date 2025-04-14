package project.comebackhomebe.domain.notification.dto.response;

import project.comebackhomebe.domain.notification.entity.Notification;

public record NotificationResponse(
        String message
) {

    public static NotificationResponse of(Notification notification) {
        return new NotificationResponse(notification.getMessage());
    }

}
