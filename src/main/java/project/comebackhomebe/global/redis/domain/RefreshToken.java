package project.comebackhomebe.global.redis.domain;

import org.springframework.data.annotation.Id;
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
@RedisHash(value = "Token", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    @Id
    private String verifyKey; // 카카오 ID

    private String accessToken; // Access 토큰

    private String refreshToken; // Refresh 토큰

    public static RefreshToken from(String verifyKey, String accessToken, String refreshToken) {
        return RefreshToken.builder()
                .verifyKey(verifyKey)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static RefreshToken update(RefreshToken refreshToken, String accessToken) {
        return RefreshToken.builder()
                .verifyKey(refreshToken.verifyKey)
                .accessToken(accessToken)
                .refreshToken(refreshToken.refreshToken)
                .build();
    }
}
