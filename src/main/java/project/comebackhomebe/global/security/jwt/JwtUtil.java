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

@Component
@Slf4j
public class JwtUtil {
    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public Role getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", Role.class);
    }

    public String getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("kakaoId", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 인증 절차 시작");
        if (token == null || isExpired(token)) {
            log.error("token expired");
            return null;
        }

        // 토큰에서 값 추출
        String username = getUsername(token);
        Role role = getRole(token);
        String kakaoId = getId(token);

        MemberInfo memberInfo = MemberInfo.to(username, role, kakaoId);

        OAuth2Info oAuth2Info = new OAuth2Info(memberInfo);
        log.info("[getAuthentication] 인증 절차 완료");

        return new UsernamePasswordAuthenticationToken(oAuth2Info, null, oAuth2Info.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("[resolveToken] 토큰 추출 : {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.info("[resolveToken]  bearer 토큰 추출 : {}", bearerToken.substring(7));
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 값만 가져옴
        }
        return null;
    }

    public String generateToken(String category, String username, Role role, String kakaoId, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("Role", role)
                .claim("kakaoId", kakaoId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String newGenerateToken(String refreshToken) {
        String username = getUsername(refreshToken);
        Role role = getRole(refreshToken);
        String kakaoId = getId(refreshToken);

        return generateToken("access", username, role, kakaoId, 10 * 60 * 1000L);
    }
}
