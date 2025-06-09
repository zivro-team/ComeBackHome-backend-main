package project.comebackhomebe.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.io.IOException;

public interface MemberService {
    // 여기서 이제 토큰 발급
    void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response);

    // v2 OAuth2 소셜 정보 가져오기
    OAuth2Response getOAuth2Data(String provider, HttpServletRequest request, HttpServletResponse response) throws IOException;

    // 활성중인 멤버 수 가져옴
    Long getActiveUserCount();

    // 전체 멤버 수 가져옴
    Long getTotalUserCount();
}
