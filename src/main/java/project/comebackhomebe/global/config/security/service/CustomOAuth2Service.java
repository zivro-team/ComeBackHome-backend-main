package project.comebackhomebe.global.config.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.KakaoRequest;
import project.comebackhomebe.domain.member.dto.KakaoResponse;
import project.comebackhomebe.domain.member.dto.MemberInfo;

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


        return null;
    }

}
