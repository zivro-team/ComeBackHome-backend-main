package project.comebackhomebe.global.security.auth.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class KakaoAppResponse implements OAuth2Response {

    private Long id;  // ✅ JSON의 "id" 필드와 매칭
    private String connected_at;  // ✅ JSON의 "connected_at" 필드와 매칭
    private Map<String, Object> properties;  // ✅ "properties" 필드를 Map으로 저장
    private Map<String, Object> kakao_account;  // ✅ "kakao_account" 필드를 Map으로 저장

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

