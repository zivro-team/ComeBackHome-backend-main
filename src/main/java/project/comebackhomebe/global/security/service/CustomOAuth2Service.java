package project.comebackhomebe.global.security.service;

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
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        // OAuth2User 값 받아오기
        OAuth2User oAuth2User = super.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }

        String verifyKey = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        String username = oAuth2Response.getName();

        String email = oAuth2Response.getEmail();

        Member existData = memberRepository.findByVerifyKey(verifyKey);

        // 새로 받은 카카오 id 가 이미 DB에 존재하면 그냥 로그인?
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
