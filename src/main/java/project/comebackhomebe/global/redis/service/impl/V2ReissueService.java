package project.comebackhomebe.global.redis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.redis.repository.RefreshTokenRepository;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class V2ReissueService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    public MemberInfo findRefreshTokenToMember(HttpServletRequest request) {
        String getRefreshToken = tokenResponseUtil.getCookie(request);

        String findVerifyKey = refreshTokenRepository.findByRefreshToken(getRefreshToken).getVerifyKey();

        Member member = memberRepository.findByVerifyKey(findVerifyKey);

        return MemberInfo.of(member);
    }

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        MemberInfo memberInfo = findRefreshTokenToMember(request);

        String newAccessToken = jwtUtil.newAccessToken(memberInfo);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        log.info("토큰 발행 완료 : {}",newAccessToken);
    }
}
