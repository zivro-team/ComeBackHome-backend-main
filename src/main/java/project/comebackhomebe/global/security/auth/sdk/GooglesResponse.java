package project.comebackhomebe.global.security.auth.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
public class GooglesResponse implements OAuth2Response {

    private String sub;
    private String name;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    private String picture;
    private String email;

    @JsonProperty("email_verified")
    private boolean emailVerified;

    private String locale;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return sub;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}


