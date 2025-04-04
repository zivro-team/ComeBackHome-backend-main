package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.domain.member.service.RestTemplateService;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RestTemplateService restTemplateService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2Info getOAuth2Info(OAuth2Response oAuth2Response) {
        String verifyKey = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        String username = oAuth2Response.getName();

        String email = oAuth2Response.getEmail();

        Member existData = memberRepository.findByVerifyKey(verifyKey);

        if (existData == null) {
            Member member = Member.from(verifyKey, username, email, Role.USER);
            memberRepository.save(member);

            MemberInfo memberInfo = MemberInfo.of(member);

            return new OAuth2Info(memberInfo);

        } else {
            MemberInfo memberInfo = MemberInfo.of(existData);

            return new OAuth2Info(memberInfo);
        }
    }

    @Override
    public void loadKakao(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        try {
            KakaoResponse oAuth2Response = restTemplateService.verifyKakaoToken(request);
            log.info("✅ KakaoResponse: " + oAuth2Response);
            OAuth2Info oAuth2Info = getOAuth2Info(oAuth2Response);
            pushToken(oAuth2Info, response);
        } catch (Exception e) {
            log.error("❌ KakaoResponse 역직렬화 실패", e);
        }
    }

    @Override
    public void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response) {

        // 유저 정보 추출
        String verifyKey = oAuth2Info.getVerifyKey();
        String username = oAuth2Info.getName();
        String email = oAuth2Info.getEmail();
        Role role = oAuth2Info.getRole();

        String accessToken = jwtUtil.generateToken("access", verifyKey, username, email, role, 10 * 60 * 1000L);
        String refreshToken = jwtUtil.generateToken("refresh", verifyKey, username, email, role, 60 * 60 * 1000L);

        response.setHeader("Authorization", accessToken);
        refreshTokenService.saveRefreshToken(verifyKey, accessToken, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }


}
