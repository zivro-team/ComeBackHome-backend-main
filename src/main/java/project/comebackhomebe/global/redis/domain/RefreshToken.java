package project.comebackhomebe.global.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * access 토큰은 헤더에, refresh 토큰은 redis에 저장
 */
@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    private String id; // 카카오 ID

    private String accessToken; // Access 토큰

    private String refreshToken; // Refresh 토큰

    public static RefreshToken from(String id, String accessToken, String refreshToken) {
        return RefreshToken.builder()
                .id(id)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static RefreshToken update(RefreshToken refreshToken, String accessToken) {
        return RefreshToken.builder()
                .id(refreshToken.id)
                .accessToken(accessToken)
                .refreshToken(refreshToken.refreshToken)
                .build();
    }
}
