package project.comebackhomebe.global.config.actuator.healthIndicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@Component
public class SystemResourceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // 현재 JVM의 런타임 정보를 제공하는 Runtime 객체를 반환 -> 메모리, 프로세서 등의 정보 얻기
        Runtime runtime = Runtime.getRuntime();

        // JVM의 메모리 관리 정보를 제공하는 MemoryMXBean 객체를 반환 -> 추가적인 메모리 정보를 얻기 위해 확장 가능성을 고려
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        double memoryUsage = (double) usedMemory / totalMemory * 100;

        // 메모리 사용률이 80% 이상이면 DOWN
        if (memoryUsage > 80) {
            return Health.down()
                    .withDetail("memoryUsage", String.format("%.2f%%", memoryUsage))
                    .withDetail("usedMemory", usedMemory)
                    .withDetail("totalMemory", totalMemory)
                    .withDetail("status", "High memory usage")
                    .build();
        }

        return Health.up()
                .withDetail("memoryUsage", String.format("%.2f%%", memoryUsage))
                .withDetail("usedMemory", usedMemory)
                .withDetail("totalMemory", totalMemory)
                .withDetail("availableProcessors", runtime.availableProcessors())
                .build();
    }
}
