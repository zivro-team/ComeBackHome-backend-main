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
import project.comebackhomebe.global.security.auth.KakaoResponse;

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

        // ID 값 가져오기
        String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id"));

        // 카카오 반환 데이터 형태 중 닉네임을 얻기 위해 설정
        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");

        // 닉네임 얻기
        KakaoResponse response = KakaoResponse.nameFrom(properties);

        // 닉네임 얻고
        String username = response.attribute();

        // 엔티티 저장
        Member member = Member.from(username, Role.USER, kakaoId);
        memberRepository.save(member);

        // DTO 넘기기
        MemberInfo memberInfo = MemberInfo.of(member);

        return new OAuth2Info(memberInfo);
    }

}
