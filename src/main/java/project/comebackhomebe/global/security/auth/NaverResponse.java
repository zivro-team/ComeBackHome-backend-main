package project.comebackhomebe.global.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Schema(description = "사용 X")
public class NaverResponse implements OAuth2Response {

    private Map<String, Object> response;

    public NaverResponse(Map<String, Object> attributes) {
        this.response = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return response.get("id").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }

    @Override
    public String getName() {
        return response.get("name").toString();
    }
}
