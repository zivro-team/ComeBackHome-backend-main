package project.comebackhomebe.global.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
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
    private String verifyKey; // 회원 ID

    private String refreshToken; // Refresh 토큰

    public static RefreshToken from(String verifyKey, String refreshToken) {
        return RefreshToken.builder()
                .verifyKey(verifyKey)
                .refreshToken(refreshToken)
                .build();
    }

    public static RefreshToken update(RefreshToken refreshToken, String newRefreshToken) {
        return RefreshToken.builder()
                .verifyKey(refreshToken.verifyKey)
                .refreshToken(newRefreshToken)
                .build();
    }
}
