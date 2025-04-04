package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.comebackhomebe.domain.member.service.RestTemplateService;
import project.comebackhomebe.global.security.auth.GoogleResponse;
import project.comebackhomebe.global.security.auth.KakaoResponse;
import project.comebackhomebe.global.security.auth.NaverResponse;
import project.comebackhomebe.global.security.auth.OAuth2Response;
import project.comebackhomebe.global.security.jwt.JwtUtil;

import java.net.URI;

@RequiredArgsConstructor
@Service
@Slf4j
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2Response verifyOAuth2Token(String provider, HttpServletRequest request) throws JsonProcessingException {
        URI uri = getProviderUri(provider);
        String token = jwtUtil.resolveToken(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // 카카오가 기대하는 헤더// 헤더 토큰 추출

        RequestEntity<?> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info(responseEntity.getBody());

        return parseResponse(provider, responseEntity.getBody());
    }

    private URI getProviderUri(String provider) {
        return switch (provider.toUpperCase()) {
            case "KAKAO" -> URI.create("https://kapi.kakao.com/v2/user/me");
            case "GOOGLE" -> URI.create("https://www.googleapis.com/oauth2/v3/userinfo");
            case "NAVER" -> URI.create("https://openapi.naver.com/v1/nid/me");
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth2 공급자입니다.");
        };
    }

    private String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }

    private OAuth2Response parseResponse(String provider, String responseBody) throws JsonProcessingException {
        return switch (provider.toUpperCase()) {
            case "KAKAO" -> objectMapper.readValue(responseBody, KakaoResponse.class);
            case "GOOGLE" -> objectMapper.readValue(responseBody, GoogleResponse.class);
            case "NAVER" -> objectMapper.readValue(responseBody, NaverResponse.class);
            default -> throw new IllegalArgumentException("지원하지 않는 OAuth2 공급자입니다.");
        };
    }

}
