package project.comebackhomebe.global.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        // OAuth2User 값 받아오기
        OAuth2User oAuth2User = super.loadUser(request);
        log.info("loadUser : {}", oAuth2User.getAttributes());

        String registrationId = request.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        switch (registrationId) {
            case "kakao" -> oAuth2Response = objectMapper.convertValue(oAuth2User.getAttributes(), KakaoResponse.class);
            case "google" -> oAuth2Response = objectMapper.convertValue(oAuth2User.getAttributes(), GoogleResponse.class);
            case "naver" -> oAuth2Response = objectMapper.convertValue(oAuth2User.getAttributes(), NaverResponse.class);
            default -> throw new IllegalArgumentException("지원되지 않는 OAuth2 로그인 제공자: " + registrationId);
        }

        String verifyKey = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        String username = oAuth2Response.getName();

        String email = oAuth2Response.getEmail();

        Member existData = memberRepository.findByVerifyKey(verifyKey);

        if (existData == null) {
            // 엔티티 저장
            Member member = Member.from(verifyKey, username, email, Role.USER);
            memberRepository.save(member);

            // DTO 넘기기
            MemberInfo memberInfo = MemberInfo.of(member);

            return new OAuth2Info(memberInfo);

        } else {
            MemberInfo memberInfo = MemberInfo.of(existData);

            return new OAuth2Info(memberInfo);
        }
    }

}
