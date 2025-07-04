package project.comebackhomebe.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.jwt.JwtService;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final TokenResponseUtil tokenResponseUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 여기서 OAUth2Info가 MemverInfo 로 케스팅함
        OAuth2Info oAuth2Info = (OAuth2Info) authentication.getPrincipal();

        // 유저 정보 추출
        String verifyKey = oAuth2Info.getVerifyKey();
        String username = oAuth2Info.getName();
        String email = oAuth2Info.getEmail();
        Role role = oAuth2Info.getRole();

        String accessToken = jwtService.generateAccessToken("access", verifyKey, username, email, role, 100000 * 60 * 1000L);
        String refreshToken = jwtService.generateRefreshToken(60 * 60 * 1000L);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.addHeader("Set-Cookie", tokenResponseUtil.createCookie("Authentication",accessToken).toString());
        response.addHeader("Set-Cookie", tokenResponseUtil.createCookie("refresh", refreshToken).toString());

        response.sendRedirect("https://cbh.kro.kr/admin");
        refreshTokenService.saveRefreshToken(verifyKey, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }
}
