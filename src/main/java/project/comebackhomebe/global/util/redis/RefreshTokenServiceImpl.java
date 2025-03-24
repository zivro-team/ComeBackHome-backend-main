package project.comebackhomebe.global.util.redis;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;

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

    @Override
    public String findRefreshToken(HttpServletRequest request) {
        String access = jwtUtil.resolveToken(request);
        log.info("[findRefreshToken] access token: {}", access);

        String id = jwtUtil.getId(access);

        String refreshToken = refreshTokenRepository.findById(id).get().getRefreshToken();
        log.info("[findRefreshToken] refresh token: {}", refreshToken);
        return refreshToken;
    }

    @Override
    public void newCreateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshToken(request);
        String newToken = jwtUtil.newGenerateToken(refreshToken);
        log.info("[newCreateAccessToken] refresh token: {}", refreshToken);
        response.setHeader("Authorization", newToken);

        String id = jwtUtil.getId(refreshToken);
        RefreshToken token = refreshTokenRepository.findById(id).get();
        RefreshToken updatedToken = RefreshToken.update(token, newToken);
        log.info("[newCreateAccessToken] update token: {}", updatedToken);
        refreshTokenRepository.save(updatedToken);
    }

    @Override
    public void deleteRefreshToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String id = jwtUtil.getId(token);
        refreshTokenRepository.deleteById(id);
    }
}
