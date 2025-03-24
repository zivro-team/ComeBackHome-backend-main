package project.comebackhomebe.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.repository.MemberRepository;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        // OAuth2User 값 받아오기
        OAuth2User oAuth2User = super.loadUser(request);
        log.info("[loadUser] 유저 값 : {}", oAuth2User);

        // 각 소셜 로그인 플랫폼 확인하기 (카카오만 사용해서 사실 필요없음)
        String registrationId = request.getClientRegistration().getRegistrationId();
        log.info("[registrationId] : {}", registrationId); // Kakao

        // ID 값 가져오기
        String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id")); // "3970874734"
        log.info("[kakaoId] : {}", kakaoId);

        // 카카오 반환 데이터 형태 중 닉네임을 얻기 위해 설정
        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
        log.info("[properties] : {}", properties);

        // 닉네임 얻기
        KakaoResponse response = KakaoResponse.nameFrom(properties); // properties 저장
        log.info("[response] : {}", response);

        // 닉네임 얻고
        String username = response.attribute();
        log.info("[username] : {}", username);

        // 엔티티 저장
        Member member = Member.from(username, Role.USER, kakaoId);
        memberRepository.save(member);
        log.info("[member] : {}", member);

        // DTO 넘기기
        MemberInfo memberInfo = MemberInfo.of(member);
        log.info("[memberInfo] : {}", memberInfo);

        return new OAuth2Info(memberInfo);
    }

}
