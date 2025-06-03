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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.CustomRateLimiter.RateLimited;
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

    /**
     * 로그인 API 입니다.
     * 소셜로그인 정보가 등록되어 있지 않은 경우 자동으로 회원가입해줍니다.
     *
     * @param provider : kakao
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/{provider}")
    @Operation(summary = "로그인 API", description = "provider = 소셜 이름")
    @Parameters({
            @Parameter(name = "JSON", description = "소셜 반환 값", example = "{}")
    })
    public ResponseEntity<OAuth2Response> signIn(@PathVariable("provider") String provider,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(memberService.getOAuth2Data(provider, request, response));
    }

    /**
     * AccessToken 만료 시 RefreshToken 으로
     * 새로운 AccessToken 을 발급해줍니다.
     *
     * @param request  : AccessToken, RefreshToken
     * @param response : newAccessToken
     */
    @PostMapping("/reissue")
    @Operation(summary = "액세스 재발급 API", description = "액세스토큰을 재발급해줍니다.")
    @Parameters({
            @Parameter(name = "Token", description = "리프레쉬 토큰 쿠키로 넘겨야해요", example = "ejkldjals.ejslklda.edkd")
    })
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        reissueService.reissueAccessToken(request, response);
    }

    /**
     * 로그아웃 API 입니다.
     * 로그아웃 시 리프레쉬 토큰을 삭제하고
     * 블랙리스트에 토큰을 담어 해당 접근을 차단합니다.
     *
     * @param request  : accessToken
     * @param response
     */
    @DeleteMapping("/logout")
    @Operation(summary = "logout API", description = "로그아웃 후 액세스 토큰은 블랙리스트, 리프레쉬 토큰 삭제합니다.")
    @Parameters({
            @Parameter(name = "accessToken", description = "액세스 토큰 헤더로", example = "ejkldjals.ejslklda.edkd"),
            @Parameter(name = "refreshToken", description = "리프레쉬 쿠키로", example = "ejkldjals.ejslklda.edkd")
    })
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.deleteRefreshToken(request, response);
        blacklistService.createBlacklist(request);
        // TODO : 비동기처리 해보기
    }

    /**
     * 웹 소켓 시 사용 예정
     *
     * @param memberInfo
     * @return
     */
    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public MemberInfo addUser(@Payload MemberInfo memberInfo) {
        return null;
    }

    /**
     * 웹 소켓 시 사용 예정
     *
     * @param memberInfo
     * @return
     */
    @MessageMapping("/user.disConnectUser")
    @SendTo("/user/topic")
    public MemberInfo disConnect(@Payload MemberInfo memberInfo) {
        return null;
    }

}
