package project.comebackhomebe.global.security.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
        return "Kakao";
    }

    @Override
    public String getProviderId() {
        return id.toString();
    }

    @Override
    public String getEmail() {
        if (kakao_account != null && kakao_account.containsKey("email")) {
            return kakao_account.get("email").toString();
        }
        return "N/A";
    }

    @Override
    public String getName() {
        if (properties != null && properties.containsKey("nickname")) {
            return properties.get("nickname").toString();
        }
        return "N/A";
    }
}

