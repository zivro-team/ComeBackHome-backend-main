package project.comebackhomebe.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "사용X", description = "사용하지 않는 API입니다.")
public class ApiV1MemberController {

    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    // 소셜 로그인
    @PostMapping("/{provider}")
    public ResponseEntity<OAuth2Response> verifyToken(@PathVariable String provider, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        return ResponseEntity.ok(memberService.loadOAuth2(provider, request, response));
    }

    // access 토큰 재발급
    @PostMapping("/reissue")
    public void reissue() {}

    // 로그아웃
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.deleteRefreshToken(request, response);
    }

}
