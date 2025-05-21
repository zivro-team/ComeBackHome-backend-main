package project.comebackhomebe.domain.notification.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "notification")
@Getter
@Builder
public class Notification {

    @Id
    private String id;

    private String token; // 안드로이드에서 제공받는 토큰

    private String title; // 제목

    private String message; // 해당 메시지

    public static Notification from(String token, String title, String message) {
        return Notification.builder()
                .token(token)
                .title(title)
                .message(message)
                .build();
    }
}
