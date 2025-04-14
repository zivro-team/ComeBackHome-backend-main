package project.comebackhomebe.domain.notification.entity;

import com.google.firebase.messaging.Message;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long following_user_id; // 알림을 받는 사용자, 기능 구현 이후 디벨롭

    private String token; // 안드로이드에서 제공받는 토큰

    private String title; // 제목

    private String message; // 해당 메시지

    public static Notification from(NotificationRequest request) {
        return Notification.builder()
                .following_user_id(request.user_id())
                .token(request.token())
                .title(request.title())
                .message(request.message())
                .build();
    }

    // FCM Message 객체 반환
    public Message getFcmMessage() {
        return Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build())
                .setToken(token)
                .build();
    }
}
