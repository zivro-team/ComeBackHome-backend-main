package project.comebackhomebe.global.security.auth;

import java.util.Map;

public record NaverResponse(Map<String, Object> attribute) implements OAuth2Response {
    private Map<String, Object> getResponse() {
        return (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getProviderId() {

        return getResponse().get("id").toString();
    }

    @Override
    public String getEmail() {

        return getResponse().get("email").toString();
    }

    @Override
    public String getName() {

        return getResponse().get("name").toString();
    }
}
