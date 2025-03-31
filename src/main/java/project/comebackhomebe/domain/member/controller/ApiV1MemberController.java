package project.comebackhomebe.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.redis.service.RefreshTokenService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    @GetMapping
    public String main() {
        return "Hello World!";
    }

    // 토큰 검증 로직
    @PostMapping("/kakao")
    public ResponseEntity<?> verifyToken(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        try {
            memberService.loadKakao(request, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
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
