package project.comebackhomebe.global.security.auth;

import lombok.NoArgsConstructor;

import java.util.Map;


@NoArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private Long id;
    private Map<String, Object> properties;
    private Map<String, Object> kakao_account;

    public KakaoResponse(Map<String, Object> attributes) {
        this.id = (Long) attributes.get("id");
        this.properties = (Map<String, Object>) attributes.get("properties");
        this.kakao_account = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return id.toString();
    }

    @Override
    public String getEmail() {
        return kakao_account != null && kakao_account.containsKey("email")
                ? kakao_account.get("email").toString()
                : "N/A";
    }

    @Override
    public String getName() {
        return properties != null && properties.containsKey("nickname")
                ? properties.get("nickname").toString()
                : "N/A";
    }
}
