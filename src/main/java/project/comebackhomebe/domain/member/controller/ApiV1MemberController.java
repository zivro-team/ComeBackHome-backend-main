package project.comebackhomebe.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.global.redis.service.RefreshTokenService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public String main (){
        return "Hello World!";
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
