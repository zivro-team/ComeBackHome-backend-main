package project.comebackhomebe.global.firebase.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.jwt.JwtService;
import project.comebackhomebe.global.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class FCMService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public String getToken(HttpServletRequest request, String fcmToken) {

        String accessToken = jwtService.resolveAccessToken(request);

        String verifyKey = jwtUtil.getVerifyKey(accessToken);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        Member.fcmTokenFrom(member, fcmToken);

        return "토큰이 성공적으로 저장되었습니다";
    }
}
