package project.comebackhomebe.domain.member.controller;

import co.elastic.clients.elasticsearch._types.Refresh;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.config.security.handler.RefreshHandler;
import project.comebackhomebe.global.config.security.jwt.JwtUtil;
import project.comebackhomebe.global.util.redis.RefreshTokenService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final MemberService memberService;
    private final RefreshHandler refreshHandler;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @GetMapping("/main")
    public String main(){
        return "성공";
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        refreshHandler.refreshHandler(request, response);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTokenService.findRefreshToken(request);
        response.setHeader("Authorization", jwtUtil.newGenerateToken(refresh));
        log.info(jwtUtil.newGenerateToken(refresh));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        refreshTokenService.deleteRefreshToken(request);
    }

}
