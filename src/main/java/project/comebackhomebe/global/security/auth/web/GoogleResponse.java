package project.comebackhomebe.global.security.auth.web;

import project.comebackhomebe.global.security.auth.OAuth2Response;

import java.util.Map;

public record GoogleResponse(Map<String, Object> attribute) implements OAuth2Response {

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
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
