package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.jwt.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RestTemplateService restTemplateService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

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
    public OAuth2Response loadOAuth2(String provider, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        OAuth2Response oAuth2Response = restTemplateService.verifyOAuth2Token(provider, request);

        OAuth2Info oAuth2Info = getOAuth2Info(oAuth2Response);

        pushToken(oAuth2Info, response);

        return oAuth2Response;
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
        refreshTokenService.saveRefreshToken(verifyKey, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }

    @Override
    public OAuth2Response getOAuth2Data(String provider, HttpServletRequest request, HttpServletResponse response) throws IOException {

        OAuth2Response oAuth2Response = parseResponse(provider, request); // 소셜에 맞는 데이터로 변환

        OAuth2Info oAuth2Info = getOAuth2Info(oAuth2Response); // DB 저장 DTO

        pushToken(oAuth2Info, response); // 토큰 생성

        return oAuth2Response;
    }


    private OAuth2Response parseResponse(String provider, HttpServletRequest request) throws IOException {

        StringBuilder jsonBuilder = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }

        String responseBody = jsonBuilder.toString();

        return switch (provider.toUpperCase()) {
            case "KAKAO" -> objectMapper.readValue(responseBody, KakaoResponse.class);
            case "GOOGLE" -> objectMapper.readValue(responseBody, GoogleResponse.class);
            case "NAVER" -> objectMapper.readValue(responseBody, NaverResponse.class);
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth2 공급자입니다.");
        };
    }


}
