package project.comebackhomebe.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.jwt.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class RefreshFilter extends OncePerRequestFilter {
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!"/reissue".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Processing /reissue request");

        String refreshToken = refreshTokenService.findRefreshToken(request, response);
        if (refreshToken == null) {
            log.warn("Refresh token not found in request");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Refresh token not found\"}");
            return;
        }
        boolean checkRefresh = jwtUtil.isValidJwt(refreshToken);

        if (checkRefresh) {
            refreshTokenService.reissueAccessToken(request, response);
        }
    }
}
