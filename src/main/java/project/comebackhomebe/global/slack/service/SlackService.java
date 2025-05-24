package project.comebackhomebe.global.slack.service;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.webhook.Payload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import project.comebackhomebe.global.exception.ErrorCode;
import project.comebackhomebe.global.slack.data.Color;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlackService {
    @Value("${slack.webhook-uri}")
    private String webhookUri;

    private final Slack slackClient = Slack.getInstance();
    private final Environment environment;

    /**
     * 슬랙에 메세지 전송
     */
    public void sendMessageToSlack(String title, Map<String, String> data) {
        try {
            slackClient.send(webhookUri, Payload.builder()
                    .text(title)
                    .attachments(List.of(
                            Attachment.builder()
                                    .color(Color.RED.getColor())
                                    .fields(data.keySet()
                                            .stream()
                                            .map(key -> generateSlackField(key, data.get(key)))
                                            .collect(Collectors.toList()))
                                    .build()))
                    .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Slack Field 생성
     **/
    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }

    public void sendSlackMessage(Exception exception, ErrorCode errorCode, HttpServletRequest request) {
        Map<String, String> data = new HashMap<>();

        // 기본 에러 정보
        data.put("🚨 에러 메시지", exception.getMessage());
        data.put("🔧 에러 코드", errorCode.name());
        data.put("📊 HTTP 상태", String.valueOf(errorCode.getHttpStatus().value()));
        data.put("⏰ 발생 시간", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 요청 정보
        if (request != null) {
            data.put("🌐 요청 URL", request.getRequestURL().toString());
            data.put("📝 HTTP 메소드", request.getMethod());
            data.put("🖥️ 클라이언트 IP", getClientIP(request));
            data.put("👤 User-Agent", request.getHeader("User-Agent"));
        }

        // 스택 트레이스 (중요한 에러의 경우)
        if (isImportantError(errorCode)) {
            data.put("📋 스택 트레이스", getStackTrace(exception));
        }

        // 환경 정보
        data.put("🏷️ 프로파일", Arrays.toString(environment.getActiveProfiles()));

        sendMessageToSlack("🔥 " + errorCode.getMessage(), data);
    }

    private boolean isImportantError(ErrorCode errorCode) {
        // CRITICAL, HIGH 레벨 에러만 스택 트레이스 포함
        return errorCode.getHttpStatus().value() >= 400;
    }

    private String getStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String stackTrace = sw.toString();

        // 너무 길면 앞부분만 (Slack 메시지 길이 제한)
        return stackTrace.length() > 1000 ? stackTrace.substring(0, 1000) + "..." : stackTrace;
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }


}
