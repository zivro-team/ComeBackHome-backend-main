package project.comebackhomebe.global.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;

@RequiredArgsConstructor
@Slf4j
@Service
public class RefreshHandler {
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    public void refreshHandler (HttpServletRequest request, HttpServletResponse response) {
        String refresh = tokenResponseUtil.getCookie(request);

        if (refresh == null) {
            log.info("Refresh token no");
            return;
        }

        if (jwtUtil.isExpired(refresh)){
            log.info("Refresh token expired");
        }

        String username = jwtUtil.getUsername(refresh);
        Role role = jwtUtil.getRole(refresh);
        String kakaoId = jwtUtil.getId(refresh);

        String newAccessToken = jwtUtil.generateToken("access", username, role, kakaoId, 10*60*1000L);

        response.setHeader("Authorization", newAccessToken);

        log.info("[newAccessToken] 새로운 접근 토큰 : {}", newAccessToken);
    }
}
