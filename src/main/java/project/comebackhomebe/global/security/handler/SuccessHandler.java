package project.comebackhomebe.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.redis.service.RefreshTokenService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 여기서 OAUth2Info가 MemverInfo 로 케스팅함
        OAuth2Info oAuth2Info = (OAuth2Info) authentication.getPrincipal();

        String username = oAuth2Info.getName();

        Role role = oAuth2Info.getRole();

        String kakaoId = oAuth2Info.getId();

        String accessToken = jwtUtil.generateToken("access", username, role, kakaoId, 10 * 60 * 1000L);
        String refreshToken = jwtUtil.generateToken("refresh", username, role, kakaoId, 60 * 60 * 1000L);

        response.setHeader("Authorization", accessToken);
        refreshTokenService.saveRefreshToken(kakaoId, accessToken, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }
}
