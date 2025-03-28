package project.comebackhomebe.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;

import java.util.Map;

public interface RestTemplateService {
    // 유효성 검증 (카카오)
    KakaoResponse verifyKakaoToken(HttpServletRequest request) throws JsonProcessingException;

    // 유효성 검증 (구글)
    String verifyGoogleToken(HttpServletRequest request, HttpServletResponse response);

    // 유효성 검증 (네이버)
    String verifyNaverToken(HttpServletRequest request, HttpServletResponse response);

}
