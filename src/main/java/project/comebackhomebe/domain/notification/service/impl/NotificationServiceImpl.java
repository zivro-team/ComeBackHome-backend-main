package project.comebackhomebe.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.dto.response.NotificationResponse;
import project.comebackhomebe.domain.notification.entity.Notification;
import project.comebackhomebe.domain.notification.repository.NotificationRepository;
import project.comebackhomebe.domain.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) throws FirebaseMessagingException {
        Notification notification = Notification.from(notificationRequest);

        notificationRepository.save(notification);

        FirebaseMessaging.getInstance().send(notification.getFcmMessage());

        return NotificationResponse.of(notification);
    }


}
