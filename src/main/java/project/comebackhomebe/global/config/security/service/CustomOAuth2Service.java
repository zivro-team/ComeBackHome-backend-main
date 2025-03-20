package project.comebackhomebe.global.config.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.KakaoResponse;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;

@Service
@Slf4j
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        log.info("[loadUser] 유저 값 : {}", oAuth2User);

        String registrationId = request.getClientRegistration().getRegistrationId();
        log.info("[registrationId] : {}", registrationId);

        KakaoResponse response = new KakaoResponse(oAuth2User.getAttributes());
        log.info("[response] : {}", response);

        String username = response.getProvider() + " " + response.getProviderId(); // uuid로 해도 되긴 함 그냥 단지 특정 값 생성
        log.info("[username] : {}", username);

        // 엔티티 저장
        Member member = Member.from(response.getName(), Role.USER, username);
        log.info("[member] : {}", member);

        // DTO 넘기기
        MemberInfo memberInfo = MemberInfo.of(member);
        log.info("[memberInfo] : {}", memberInfo);

        return new OAuth2Info(memberInfo);
    }

}
