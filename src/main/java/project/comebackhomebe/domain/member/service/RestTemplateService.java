package project.comebackhomebe.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import project.comebackhomebe.global.security.auth.OAuth2Response;

public interface RestTemplateService {
    // 소셜 로그인 유효성 검증
    OAuth2Response verifyOAuth2Token(String provider, HttpServletRequest request) throws JsonProcessingException;
}
