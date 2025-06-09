package project.comebackhomebe.global.config.actuator.healthIndicator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    // 데이터베이스 연결하는 객체 (JDBC 사용 시 사용)
    private final DataSource dataSource;

    @Override
    public Health health() {
        // 데이터베이스에 연결 시도
        try (Connection connection = dataSource.getConnection()) {
            // 데이터베이스 연결이 유효한지
            if (connection.isValid(1)) {
                // 데이터베이스가 정상 상태임을 나타냄
                return Health.up()
                        .withDetail("database", "Available")
                        .withDetail("validationQuery", "SELECT 1")
                        .build();
            }
        } catch (SQLException e) {
            // 데이터베이스에 연결 문제가 발생
            return Health.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
        // connection.isValid(1)이 false를 반환하거나 다른 이유로 예외가 발생하지 않고 연결이 실패한 경우
        return Health.down().withDetail("database", "Connection invalid").build();

    }
}
