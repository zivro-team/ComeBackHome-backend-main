package project.comebackhomebe.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    @PostMapping("/{provider}")
    public ResponseEntity<OAuth2Response> verifyToken(@PathVariable String provider, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        return ResponseEntity.ok(memberService.loadOAuth2(provider, request, response));
    }

    // access 토큰 재발급
    @PostMapping("/reissue")
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.reissueAccessToken(request, response);
    }

    // 로그아웃
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        refreshTokenService.deleteRefreshToken(request);
    }

}
