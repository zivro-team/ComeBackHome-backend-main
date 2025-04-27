package project.comebackhomebe.global.firebase.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.firebase.dto.FCMTokenRequest;

@Service
@RequiredArgsConstructor
public class FCMService {

    private final MemberRepository memberRepository;

    public String getToken(Long userId, String token) {

        Member member = memberRepository.findById(userId).orElse(null);

        member.setFcmToken(token);

        memberRepository.save(member);

        return "토큰이 성공적으로 저장되었습니다";
    }
}
