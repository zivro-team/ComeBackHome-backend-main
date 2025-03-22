package project.comebackhomebe.domain.member.dto;

import java.util.Map;

public record KakaoResponse(String attribute) {

    public static KakaoResponse nameFrom(Map<String, Object> attribute) {
        return new KakaoResponse( (String) attribute.get("nickname"));
    }
}
