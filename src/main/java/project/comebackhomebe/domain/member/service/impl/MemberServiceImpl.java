package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.exception.ErrorCode;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.jwt.JwtService;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.security.service.SocialMemberService;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final SocialMemberService socialMemberService;
    private final TokenResponseUtil tokenResponseUtil;

    @Override
    public void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response) {

        // 유저 정보 추출
        String verifyKey = oAuth2Info.getVerifyKey();
        String username = oAuth2Info.getName();
        String email = oAuth2Info.getEmail();
        Role role = oAuth2Info.getRole();

        String accessToken = jwtService.generateAccessToken("access", verifyKey, username, email, role, 10 * 60 * 1000L);
        String refreshToken = jwtService.generateRefreshToken(60 * 60 * 1000L);

        response.setHeader("Authorization", accessToken);
        response.addHeader("Set-Cookie", tokenResponseUtil.createCookie("refresh", refreshToken).toString());
        refreshTokenService.saveRefreshToken(verifyKey, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }

    @Override
    public OAuth2Response getOAuth2Data(String provider, HttpServletRequest request, HttpServletResponse response) throws IOException {

        OAuth2Response oAuth2Response = parseResponse(provider, request); // 소셜에 맞는 데이터로 변환

        OAuth2Info oAuth2Info = socialMemberService.findOrCreateMember(oAuth2Response); // DB 저장 DTO

        pushToken(oAuth2Info, response);

        return oAuth2Response;
    }

    public OAuth2Response parseResponse(String provider, HttpServletRequest request) throws IOException {
        return switch (provider.toLowerCase()) {
            case "google" -> objectMapper.readValue(request.getInputStream(), GoogleResponse.class);
            case "kakao" -> objectMapper.readValue(request.getInputStream(), KakaoResponse.class);
            case "naver" -> objectMapper.readValue(request.getInputStream(), NaverResponse.class);
            default -> throw new MemberException(ErrorCode.INVALID_SOCIAL_ID);
        };
    }

}
