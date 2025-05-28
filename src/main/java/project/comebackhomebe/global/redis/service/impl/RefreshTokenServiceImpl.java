package project.comebackhomebe.global.redis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.global.redis.domain.RefreshToken;
import project.comebackhomebe.global.redis.repository.RefreshTokenRepository;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenResponseUtil tokenResponseUtil;

    /**
     * 리프레쉬 토큰을 레디스에 저장합니다.
     * @param id
     * @param refreshToken
     */
    @Override
    public void saveRefreshToken(String id, String refreshToken) {
        RefreshToken token = RefreshToken.from(id, refreshToken);

        refreshTokenRepository.save(token);
    }

    /**
     * 리프레쉬 토큰을 삭제합니다.
     * @param request
     * @param response
     */
    @Override
    public void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = tokenResponseUtil.getCookie(request);

        refreshTokenRepository.deleteById(refreshToken);

        tokenResponseUtil.expiredCookie(response);
    }

}
