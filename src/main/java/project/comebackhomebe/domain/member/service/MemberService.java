package project.comebackhomebe.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.io.IOException;

public interface MemberService {
    // OAuth2Info 로 전달
    OAuth2Info getOAuth2Info(OAuth2Response oAuth2Response);

    // JSON 반환 타입을 이제 다시 설정하는거지 여기서
    OAuth2Response loadOAuth2(String provider, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException;

    // 여기서 이제 토큰 발급
    void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response);

    // v2 OAuth2 소셜 정보 가져오기
    OAuth2Response getOAuth2Data(String provider, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
