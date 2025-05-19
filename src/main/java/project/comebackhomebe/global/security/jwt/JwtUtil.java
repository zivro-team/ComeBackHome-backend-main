package project.comebackhomebe.global.security.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Role;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public boolean isValidJwt(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        // JWT는 점(.)으로 구분된 3개 부분이어야 함
        return token.split("\\.").length == 3;
    }

    public String getVerifyKey(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("verifyKey", String.class);
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

    public Role getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", Role.class);
    }

    public String getCategory(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("category", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            if (!isValidJwt(token)) {
                return true; // 유효하지 않은 토큰은 만료된 것으로 간주
            }
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return true; // 파싱 실패 시 만료로 간주
        }
    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 인증 절차 시작");
        if (token == null || isExpired(token)) {
            log.error("token expired");
            return null;
        }

        // 토큰에서 값 추출
        String verifyKey = getVerifyKey(token);
        String username = getUsername(token);
        String email = getEmail(token);
        Role role = getRole(token);

        MemberInfo memberInfo = MemberInfo.to(verifyKey, username, email, role);

        OAuth2Info oAuth2Info = new OAuth2Info(memberInfo);
        log.info("[getAuthentication] 인증 절차 완료");

        return new UsernamePasswordAuthenticationToken(oAuth2Info, null, oAuth2Info.getAuthorities());
    }
}
