package project.comebackhomebe.global.security.auth;

import java.util.Map;

public record NaverResponse(Map<String, Object> attribute) implements OAuth2Response {
    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        return attribute.get("email").toString();
    }

    @Override
    public String getName() {

        return attribute.get("name").toString();
    }
}
