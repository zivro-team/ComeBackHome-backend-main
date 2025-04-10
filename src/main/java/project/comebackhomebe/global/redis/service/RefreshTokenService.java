package project.comebackhomebe.global.redis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshTokenService {
    // refresh 토큰을 Redis에 저장
    void saveRefreshToken(String id, String accessToken, String refreshToken);

    void updateRefreshToken(String oldToken, String newToken);

    // access 토큰으로 사용자 ID 추출 (jwtUtil) -> 사용자 ID로 refresh 토큰 찾기
    String findRefreshToken(HttpServletRequest request, HttpServletResponse response);

    // refresh 에 있는 정보로 access 토큰 발급
    void reissueAccessToken(HttpServletRequest request, HttpServletResponse response);

    // 로그아웃 시 refresh 토큰 삭제
    void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
