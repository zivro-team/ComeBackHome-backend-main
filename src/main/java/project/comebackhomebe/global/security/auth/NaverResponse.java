package project.comebackhomebe.global.security.auth;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class NaverResponse implements OAuth2Response {

    private Map<String, Object> getResponse;

    public NaverResponse(Map<String, Object> attributes) {
        this.getResponse = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getProviderId() {

        return getResponse.get("id").toString();
    }

    @Override
    public String getEmail() {

        return getResponse.get("email").toString();
    }

    @Override
    public String getName() {

        return getResponse.get("name").toString();
    }
}
