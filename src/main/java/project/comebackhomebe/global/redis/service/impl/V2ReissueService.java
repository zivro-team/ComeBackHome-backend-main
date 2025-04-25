package project.comebackhomebe.global.redis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.redis.domain.RefreshToken;
import project.comebackhomebe.global.redis.repository.RefreshTokenRepository;
import project.comebackhomebe.global.security.jwt.JwtUtil;
import project.comebackhomebe.global.security.service.TokenResponseUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class V2ReissueService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenResponseUtil tokenResponseUtil;

    public MemberInfo findRefreshTokenToMember(HttpServletRequest request) {
        // 1. 쿠키에서 리프레시 토큰 추출
        String refreshToken = tokenResponseUtil.getCookie(request);
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.error("리프레시 토큰이 쿠키에 없습니다.");
            throw new IllegalArgumentException("리프레시 토큰이 없습니다.");
        }
        log.info("추출된 리프레시 토큰: {}", refreshToken);

        // 2. Redis에서 리프레시 토큰 조회
        Optional<RefreshToken> refreshTokenOpt;
        try {
            refreshTokenOpt = refreshTokenRepository.findById(refreshToken);
        } catch (Exception e) {
            log.error("Redis 조회 실패: {}", e.getMessage(), e);
            throw new IllegalStateException("Redis 연결 오류");
        }
        if (refreshTokenOpt.isEmpty()) {
            log.error("Redis에서 리프레시 토큰을 찾을 수 없습니다: {}", refreshToken);
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        RefreshToken refreshTokenEntity = refreshTokenOpt.get();
        log.info("Redis 조회 결과: verifyKey={}, refreshToken={}",
                refreshTokenEntity.getVerifyKey(), refreshTokenEntity.getRefreshToken());

        // 3. 인증 키 추출
        String verifyKey = refreshTokenEntity.getVerifyKey();
        if (verifyKey == null || verifyKey.isEmpty()) {
            log.error("인증 키가 없습니다: {}", refreshToken);
            throw new IllegalStateException("인증 키가 없습니다.");
        }
        log.info("인증 키: {}", verifyKey);

        // 4. 인증 키로 회원 조회
        Member member = memberRepository.findByVerifyKey(verifyKey);
        if (member == null) {
            log.error("인증 키에 해당하는 회원을 찾을 수 없습니다: {}", verifyKey);
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        log.info("조회된 회원: {}", member);

        // 5. MemberInfo 반환
        return MemberInfo.of(member);
    }

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        MemberInfo memberInfo = findRefreshTokenToMember(request);

        String newAccessToken = jwtUtil.newAccessToken(memberInfo);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        log.info("토큰 발행 완료 : {}", newAccessToken);
    }

    public void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = tokenResponseUtil.getCookie(request);

        refreshTokenRepository.deleteById(refreshToken);

        tokenResponseUtil.expiredCookie(response);
    }
}
