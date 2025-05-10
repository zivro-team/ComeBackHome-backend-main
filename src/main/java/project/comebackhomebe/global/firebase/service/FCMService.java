package project.comebackhomebe.global.firebase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class FCMService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public String getToken(String accessToken, String fcmToken) {

        String verifyKey = jwtUtil.getVerifyKey(accessToken);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        Member setMember = Member.fcmTokenFrom(member, fcmToken);

        memberRepository.save(setMember);

        return "토큰이 성공적으로 저장되었습니다";
    }
}
