package project.comebackhomebe.global.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 여기서 OAUth2Info가 MemverInfo 로 케스팅함
        OAuth2Info oAuth2Info = (OAuth2Info) authentication.getPrincipal();

        String username = oAuth2Info.getName();

        Role role = oAuth2Info.getRole();

        String kakaoId = oAuth2Info.getId();

        String accessToken = jwtUtil.generateToken(username, role, kakaoId, 10 * 60L);
        String refreshToken = jwtUtil.generateToken(username, role, kakaoId, 60 * 60L);

        response.setHeader("access", accessToken);
        response.addCookie(tokenResponseUtil.createCookie("refresh", refreshToken));
        response.sendRedirect("localhost:8085/main");

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }
}
