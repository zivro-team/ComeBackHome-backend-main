//package project.comebackhomebe.global.config.security.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import project.comebackhomebe.global.config.security.handler.FailureHandler;
//import project.comebackhomebe.global.config.security.handler.SuccessHandler;
//import project.comebackhomebe.global.config.security.jwt.JwtUtil;
//
//@RequiredArgsConstructor
//@Slf4j
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final SuccessHandler successHandler;
//    private final FailureHandler failureHandler;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//
//        String username = obtainUsername(request);
//
//
//    }
//
//    // 성공 시
//    // 여기서 로그인이 완료되면 토큰을 발급하는 형식
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
//        successHandler.successHandler(request, response, chain, authentication);
//    }
//
//    // 실패 시
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
//        failureHandler.failHandle(request, response, failed);
//    }
//}
