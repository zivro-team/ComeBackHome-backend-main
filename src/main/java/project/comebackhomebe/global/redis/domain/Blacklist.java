package project.comebackhomebe.global.redis.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "Blacklist", timeToLive = 10 * 60 * 24 * 14)
public class Blacklist {

    @Id
    private String accessToken;

    private String verifyKey;

    public static Blacklist from(String verifyKey, String accessToken) {
        return Blacklist.builder()
                .verifyKey(verifyKey)
                .accessToken(accessToken)
                .build();
    }
}
