package project.comebackhomebe.domain.member.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoResponse {

    public KakaoResponse(Map<String, Object> attributes) {
    }

    String provider;

    String providerId;

    String email;

    String name;

}
