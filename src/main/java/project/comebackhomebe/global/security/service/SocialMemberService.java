package project.comebackhomebe.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@RequiredArgsConstructor
@Service
public class SocialMemberService {
    private final MemberRepository memberRepository;

    public OAuth2Info findOrCreateMember(OAuth2Response oAuth2Response) {
        String verifyKey = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        String username = oAuth2Response.getName();
        String email = oAuth2Response.getEmail();

        Member member = memberRepository.findByVerifyKey(verifyKey);

        if (member == null) {
            member = Member.from(verifyKey, username, email, Role.USER);
            memberRepository.save(member);
        }

        MemberInfo memberInfo = MemberInfo.of(member);
        return new OAuth2Info(memberInfo);
    }
}

