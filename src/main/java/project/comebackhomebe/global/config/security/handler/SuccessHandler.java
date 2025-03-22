package project.comebackhomebe.global.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    public void successHandler(HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();

        String username = memberInfo.username();

        Role role = memberInfo.role();

        String accessToken = jwtUtil.generateToken(username, role, 10*60L);
        String refreshToken = jwtUtil.generateToken(username, role, 60*60L);

        response.setHeader("access", accessToken);
        response.addCookie(tokenResponseUtil.createCookie("refresh", refreshToken));

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }
}
