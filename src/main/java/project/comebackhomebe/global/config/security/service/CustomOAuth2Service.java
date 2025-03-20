package project.comebackhomebe.global.config.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.KakaoRequest;
import project.comebackhomebe.domain.member.dto.MemberInfo;

@Service
@Slf4j
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    @Override
    public MemberInfo loadUser(KakaoRequest kakaoRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(kakaoRequest);
        log.info("[loadUser] 유저 값 : {}", oAuth2User);

        String regestraionId = kakaoRequest.getClass().get



    }

}
