package project.comebackhomebe.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "notification")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    /**
     * 알림 고유 ID
     */
    @Id
    private String id;

    /**
     * 알림 제목
     */
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 알림 내용
     */
    @Field(type = FieldType.Text)
    private String message;

    /**
     * FCM 토큰
     */
    @Field(type = FieldType.Text)
    private String token;

    /**
     * 알림 시간
     */
    @Field(name = "@timestamp", type = FieldType.Date)
    private LocalDateTime timestamp;

    /**
     * 알림 빌더 형식 생성
     * @param token : FCM 토큰
     * @param title : 강아지 발견!
     * @param message : 주위에서 강아지가 발견되었어요!
     * @return
     */
    public static Notification from(String token, String title, String message) {
        return Notification.builder()
                .token(token)
                .title(title)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
