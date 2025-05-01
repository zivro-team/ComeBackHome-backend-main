package project.comebackhomebe.global.security.auth.sdk;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
@Schema(description = "구글 DTO")
public class GooglesResponse implements OAuth2Response {

    private String sub;

    private String name;

    private String email;

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


