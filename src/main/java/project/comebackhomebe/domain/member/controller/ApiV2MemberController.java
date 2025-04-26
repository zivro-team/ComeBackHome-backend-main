package project.comebackhomebe.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.redis.service.impl.BlacklistService;
import project.comebackhomebe.global.redis.service.impl.V2ReissueService;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/member")
@RequiredArgsConstructor
@Slf4j
public class ApiV2MemberController {
    private final MemberService memberService;
    private final V2ReissueService refreshTokenService;
    private final BlacklistService blacklistService;

    @PostMapping("/{provider}")
    public ResponseEntity<OAuth2Response> login(@PathVariable("provider") String provider,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(memberService.getOAuth2Data(provider, request, response));
    }

    @PostMapping("/reissue")
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.reissueAccessToken(request, response);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.deleteRefreshToken(request, response);
        blacklistService.createBlacklist(request);
    }

    @GetMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("blacklist test");
    }
}
