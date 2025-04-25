package project.comebackhomebe.global.redis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.global.redis.domain.RefreshToken;
import project.comebackhomebe.global.redis.repository.RefreshTokenRepository;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    @Override
    public void saveRefreshToken(String id, String refreshToken) {
        RefreshToken token = RefreshToken.from(id, refreshToken);
        log.info("Saving refresh token: {}", token);

        refreshTokenRepository.save(token);
    }

    @Override
    public void updateRefreshToken(String refreshToken, String newAccessToken, String newRefreshToken) {
        String verifyKey = jwtUtil.getVerifyKey(refreshToken);

        RefreshToken token = refreshTokenRepository.findByVerifyKey(verifyKey);

        RefreshToken updatedToken = RefreshToken.update(token, newRefreshToken);

        refreshTokenRepository.save(updatedToken);
    }

    @Override
    public String findRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = tokenResponseUtil.getCookie(request);

        String verifyKey = jwtUtil.getVerifyKey(refreshToken);

        return refreshTokenRepository.findByVerifyKey(verifyKey).getRefreshToken();
    }

    @Override
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshToken(request, response);

        String newAccessToken = jwtUtil.newGenerateToken(refreshToken, "access");
        String newRefreshToken = jwtUtil.newGenerateToken(refreshToken, "refresh");
        log.info("[newCreateAccessToken] New Access Token: {}", newAccessToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        updateRefreshToken(refreshToken, newAccessToken, newRefreshToken);
    }

    @Override
    public void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshToken(request, response);

        String verifyKey = jwtUtil.getVerifyKey(refreshToken);
        log.info("[deleteRefreshToken] Refresh Token: {}", refreshToken);

        refreshTokenRepository.deleteById(verifyKey);
    }

    // refresh Rotate
    public void refreshRotate(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshToken(request, response);
    }
}
