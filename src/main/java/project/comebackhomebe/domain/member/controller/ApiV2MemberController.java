package project.comebackhomebe.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.redis.service.impl.BlacklistService;
import project.comebackhomebe.global.redis.service.impl.ReissueService;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/member")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "멤버 관련 라우터", description = "유저 API입니다.")
public class ApiV2MemberController {
    private final MemberService memberService;
    private final ReissueService reissueService;
    private final BlacklistService blacklistService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/{provider}")
    @Operation(summary = "로그인 API", description = "provider = 소셜 이름")
    @Parameters({
            @Parameter(name = "JSON", description = "소셜 반환 값", example = "{}")
    })
    public ResponseEntity<OAuth2Response> login(@PathVariable("provider") String provider,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(memberService.getOAuth2Data(provider, request, response));
    }

    @PostMapping("/reissue")
    @Operation(summary = "액세스 재발급 API", description = "액세스토큰을 재발급해줍니다.")
    @Parameters({
            @Parameter(name = "Token", description = "리프레쉬 토큰 쿠키로 넘겨야해요", example = "ejkldjals.ejslklda.edkd")
    })
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        reissueService.reissueAccessToken(request, response);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "logout API", description = "로그아웃 후 액세스 토큰은 블랙리스트, 리프레쉬 토큰 삭제합니다.")
    @Parameters({
            @Parameter(name = "accessToken", description = "액세스 토큰 헤더로", example = "ejkldjals.ejslklda.edkd"),
            @Parameter(name = "refreshToken", description = "리프레쉬 쿠키로", example = "ejkldjals.ejslklda.edkd")
    })
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.deleteRefreshToken(request, response);
        blacklistService.createBlacklist(request);
    }

}
