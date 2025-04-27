package project.comebackhomebe.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.dto.response.NotificationResponse;
import project.comebackhomebe.domain.notification.entity.Notification;
import project.comebackhomebe.domain.notification.repository.NotificationRepository;
import project.comebackhomebe.domain.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) throws FirebaseMessagingException {
        Notification notification = Notification.from(notificationRequest);

        notificationRepository.save(notification);

        FirebaseMessaging.getInstance().send(notification.getFcmMessage());

        return NotificationResponse.of(notification);
    }

    @Override
    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        // 성공 로그 남기기
        log.info("FCM 메시지 전송 성공, response: {}", response);
    }


}
