package project.comebackhomebe.global.config.actuator.healthIndicator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.service.MemberService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BusinessLogicHealthIndicator implements HealthIndicator {

    private final MemberService memberService;

    @Override
    public Health health() {
        try {
            // 핵심 비즈니스 로직 테스트
            // 활성중인 멤버 / 전체 멤버 비율
            long activeUsers = memberService.getActiveUserCount();
            long totalUsers = memberService.getTotalUserCount();

            if (activeUsers > 0 && totalUsers > 0) {
                double activeRatio = (double) activeUsers / totalUsers;

                return Health.up()
                        .withDetail("activeUsers", activeUsers)
                        .withDetail("totalUsers", totalUsers)
                        .withDetail("activeRatio", activeRatio)
                        .withDetail("lastCheck", LocalDateTime.now())
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("businessLogic", "Service unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.down().withDetail("businessLogic", "No active users").build();
    }
}

