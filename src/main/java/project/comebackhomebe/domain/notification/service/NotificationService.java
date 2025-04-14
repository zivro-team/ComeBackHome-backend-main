package project.comebackhomebe.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.dto.response.NotificationResponse;

public interface NotificationService {
    // 알림 보내기
    NotificationResponse sendNotification(NotificationRequest notificationRequest) throws FirebaseMessagingException;
}
