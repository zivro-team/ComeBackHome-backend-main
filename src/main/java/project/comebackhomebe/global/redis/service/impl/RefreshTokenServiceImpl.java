package project.comebackhomebe.global.redis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;
import project.comebackhomebe.global.redis.repository.RefreshTokenRepository;
import project.comebackhomebe.global.redis.domain.RefreshToken;
import project.comebackhomebe.global.redis.service.RefreshTokenService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void saveRefreshToken(String id, String accessToken, String refreshToken) {
        RefreshToken token = RefreshToken.from(id, accessToken, refreshToken);

        refreshTokenRepository.save(token);
    }

    @Transactional
    @Override
    public void updateRefreshToken(String refreshToken, String newToken) {
        String kakaoId = jwtUtil.getId(refreshToken);

        RefreshToken token = refreshTokenRepository.findById(kakaoId).get();

        RefreshToken updatedToken = RefreshToken.update(token, newToken);

        refreshTokenRepository.save(updatedToken);
    }

    @Override
    public String findRefreshToken(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);

        String kakaoId = jwtUtil.getId(accessToken);

        return refreshTokenRepository.findById(kakaoId).get().getRefreshToken();
    }

    @Transactional
    @Override
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshToken(request);

        String newToken = jwtUtil.newGenerateToken(refreshToken);
        log.info("[newCreateAccessToken] New Access Token: {}", newToken);

        response.setHeader("Authorization", newToken);

        updateRefreshToken(refreshToken, newToken);
    }

    @Override
    public void deleteRefreshToken(HttpServletRequest request) {
        String refreshToken = findRefreshToken(request);

        String id = jwtUtil.getId(refreshToken);

        refreshTokenRepository.deleteById(id);
    }
}
