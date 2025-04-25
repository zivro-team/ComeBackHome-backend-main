package project.comebackhomebe.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.redis.service.impl.V2ReissueService;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/member")
@RequiredArgsConstructor
public class ApiV2MemberController {
    private final MemberService memberService;
    private final V2ReissueService refreshTokenService;

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
}
