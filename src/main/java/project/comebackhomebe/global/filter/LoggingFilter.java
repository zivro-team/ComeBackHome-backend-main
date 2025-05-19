package project.comebackhomebe.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 테스트
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().toLowerCase().contains("/api")) {
            long time = System.currentTimeMillis();
            try{
                filterChain.doFilter(request, response);
            } finally {
                time = System.currentTimeMillis() - time;
                log.info("{}, {}, {}, {}ms", request.getRequestURI(), request.getMethod(), request.getContentType(), time);
            }

        }else {
            filterChain.doFilter(request, response);
        }
    }
}
