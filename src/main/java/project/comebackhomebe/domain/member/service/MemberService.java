package project.comebackhomebe.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.comebackhomebe.domain.member.dto.OAuth2Info;
import project.comebackhomebe.global.security.auth.OAuth2Response;

public interface MemberService {
    // OAuth2Info 로 전달
    OAuth2Info getOAuth2Info(OAuth2Response oAuth2Response);

    // JSON 반환 타입을 이제 다시 설정하는거지 여기서
    void loadKakao(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException;

    // 여기서 이제 토큰 발급
    void pushToken(OAuth2Info oAuth2Info, HttpServletResponse response);
}
