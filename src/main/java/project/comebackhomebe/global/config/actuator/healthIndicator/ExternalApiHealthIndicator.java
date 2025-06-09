package project.comebackhomebe.global.config.actuator.healthIndicator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ExternalApiHealthIndicator implements HealthIndicator {

    // 서버 간 연동 시 사용
    private final RestTemplate restTemplate;

    // 외부 API 받아오기
    @Value("${external.api.url}")
    private String externalApiUrl;

    @Override
    public Health health() {
        try {
            // 외부 api의 헬스체크를 받아와 String 형태로 반환
            ResponseEntity<String> response = restTemplate.getForEntity(
                    externalApiUrl + "/health", String.class);

            // 응답상태가 200 ~ 299 이내 인지 확인
            if (response.getStatusCode().is2xxSuccessful()) {
                return Health.up()
                        .withDetail("externalApi", "Available")
                        .withDetail("responseTime", System.currentTimeMillis())
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("externalApi", "Unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.down().withDetail("externalApi", "Unknown error").build();
    }
}

