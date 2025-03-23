package project.comebackhomebe.global.config.security.auth;

import java.util.Map;

public record KakaoResponse(String attribute) {

    public static KakaoResponse nameFrom(Map<String, Object> attribute) {
        return new KakaoResponse( (String) attribute.get("nickname"));
    }
}
