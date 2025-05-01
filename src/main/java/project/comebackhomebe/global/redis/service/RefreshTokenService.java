package project.comebackhomebe.global.redis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshTokenService {
    // refresh 토큰을 Redis에 저장
    void saveRefreshToken(String id, String refreshToken);

    // RefreshToken 삭제
    void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
