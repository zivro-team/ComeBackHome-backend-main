package project.comebackhomebe.global.security.auth;

import java.util.Map;

public record KakaoResponse(Map<String, Object> attributes) implements OAuth2Response {

    private Map<String, Object> getProperties() {
        return (Map<String, Object>) attributes.get("properties");
    }

    private Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }


    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return getKakaoAccount().get("email").toString();
    }

    @Override
    public String getName() {
        return getProperties().get("nickname").toString();
    }
}
