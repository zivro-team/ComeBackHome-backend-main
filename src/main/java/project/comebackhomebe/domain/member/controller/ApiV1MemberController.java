package project.comebackhomebe.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.global.util.redis.RefreshTokenService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final RefreshTokenService refreshTokenService;

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
