package project.comebackhomebe.domain.member.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.comebackhomebe.domain.member.service.RestTemplateService;
import project.comebackhomebe.global.security.auth.KakaoResponse;
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
    public KakaoResponse verifyKakaoToken(HttpServletRequest request) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // KAKAO 에서 주는건 jwt가 아님
        String token = jwtUtil.resolveToken(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // 카카오가 기대하는 헤더// 헤더 토큰 추출

        RequestEntity<?> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info(responseEntity.getBody());

        // 여기서 null 발생
        return objectMapper.readValue(responseEntity.getBody(), KakaoResponse.class);
    }

    @Override
    public String verifyGoogleToken(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    @Override
    public String verifyNaverToken(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    public String resolveToken(HttpServletRequest request) {
        String socialToken = request.getHeader("Authorization");
        if (socialToken != null && socialToken.startsWith("Bearer ")) {
            log.info("[resolveToken]  bearer 토큰 추출 : {}", socialToken.substring(7));
            return socialToken.substring(7); // "Bearer " 이후의 토큰 값만 가져옴
        }
        return null;
    }

}
