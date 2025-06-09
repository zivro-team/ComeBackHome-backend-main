package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.entity.UserStatus;
import project.comebackhomebe.domain.member.exception.MemberException;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.exception.ErrorCode;
import project.comebackhomebe.global.redis.service.RefreshTokenService;
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.jwt.JwtService;
import project.comebackhomebe.global.security.service.SocialMemberService;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final SocialMemberService socialMemberService;
    private final TokenResponseUtil tokenResponseUtil;
    private final MemberRepository memberRepository;

    /**
     * 인가서버에서 각 소셜에 맞는 데이터를 반환
     * 미리 등록되어 있는 사용자가 있으면 DB에서 반환
     * 등록되어 있지 않으면 새로 생성
     * 이후 해당 토큰을 만들어 푸쉬
     *
     * @param provider : kakao, google, naver
     * @param request  : payload
     * @param response : accessToken, refreshToken
     * @return
     * @throws IOException
     */
    @Override
    public OAuth2Response getOAuth2Data(String provider, HttpServletRequest request, HttpServletResponse response) throws IOException {

        OAuth2Response oAuth2Response = parseResponse(provider, request); // 소셜에 맞는 데이터로 변환

        OAuth2Info oAuth2Info = socialMemberService.findOrCreateMember(oAuth2Response); // DB 저장 DTO

        pushToken(oAuth2Info, response);

        return oAuth2Response;
    }

    @Override
    public Long getActiveUserCount() {
       return memberRepository.countByUserStatus(UserStatus.ONLINE);
    }

    @Override
    public Long getTotalUserCount() {
        return memberRepository.count();
    }

    /**
     * 해당 URI 주소에서 provider 파싱 후
     * 맞는 소셜로 리다이렉트
     *
     * @param provider : kakao, google, naver
     * @param request  : payload
     * @return
     * @throws IOException
     */
    public OAuth2Response parseResponse(String provider, HttpServletRequest request) throws IOException {
        return switch (provider.toLowerCase()) {
            case "google" -> objectMapper.readValue(request.getInputStream(), GoogleResponse.class);
            case "kakao" -> objectMapper.readValue(request.getInputStream(), KakaoResponse.class);
            case "naver" -> objectMapper.readValue(request.getInputStream(), NaverResponse.class);
            default -> throw new MemberException(ErrorCode.INVALID_SOCIAL_ID);
        };
    }

    /**
     * 각 소셜 데이터를 받은 후 username, email, role, verifykey를 넣어 토큰 생성
     * 이후 액세스는 헤더에, 리프레쉬는 토큰에 넣기
     *
     * @param oAuth2Info : 소셜 데이터
     * @param response   : accessToken, refreshToken
     */
    @Override
    public void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response) {

        // 유저 정보 추출
        String verifyKey = oAuth2Info.getVerifyKey();
        String username = oAuth2Info.getName();
        String email = oAuth2Info.getEmail();
        Role role = oAuth2Info.getRole();

        String accessToken = jwtService.generateAccessToken("access", verifyKey, username, email, role, 30 * 60 * 1000L);
        String refreshToken = jwtService.generateRefreshToken(60 * 60 * 1000L);

        response.setHeader("Authorization", accessToken);
        response.addHeader("Set-Cookie", tokenResponseUtil.createCookie("refresh", refreshToken).toString());
        refreshTokenService.saveRefreshToken(verifyKey, refreshToken);

        log.info("Access Token: {}", accessToken);
        log.info("Refresh Token: {}", refreshToken);
    }

}
