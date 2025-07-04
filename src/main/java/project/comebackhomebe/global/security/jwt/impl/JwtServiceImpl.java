package project.comebackhomebe.global.security.jwt.impl;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.global.exception.ErrorCode;
import project.comebackhomebe.global.security.jwt.JwtService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    @Override
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.info("[resolveToken]  bearer 토큰 추출 : {}", bearerToken.substring(7).trim());
            return bearerToken.substring(7).trim(); // "Bearer " 이후의 토큰 값만 가져옴
        }
        return null;
    }

    /**
     * HTTP 요청에서 Authorization 헤더를 추출합니다.
     */
    private String getAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            // Bearer 제거 + trim
            return header.substring(7).trim();
        }
        return null;
    }

    @Override
    public String resolveRefreshToken(HttpServletRequest request) {
        // TODO
        return "";
    }

    @Override
    public String generateAccessToken(String category, String verifyKey, String username, String email, Role role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("verifyKey", verifyKey)
                .claim("username", username)
                .claim("email", email)
                .claim("Role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(Long expiredMs){
        return Jwts.builder()
                .claim("uuid", UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateNewAccessToken(MemberInfo memberInfo, String type) {
        String verifyKey = memberInfo.verifyKey();
        String username = memberInfo.username();
        String email = memberInfo.email();
        Role role = memberInfo.role();

        return generateAccessToken(type, verifyKey, username, email, role, 10 * 60 * 1000L);
    }

    @Override
    public String generateNewRefreshToken() {
        return generateRefreshToken(60 * 60 * 1000L);
    }
}
