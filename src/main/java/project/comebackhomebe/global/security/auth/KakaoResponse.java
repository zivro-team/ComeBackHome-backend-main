package project.comebackhomebe.global.security.auth;

import java.util.Map;

public record KakaoResponse(String attribute) {

    public static KakaoResponse nameFrom(Map<String, Object> attribute) {
        return new KakaoResponse((String) attribute.get("nickname"));
    }
}
