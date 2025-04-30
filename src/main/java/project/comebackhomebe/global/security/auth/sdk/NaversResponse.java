package project.comebackhomebe.global.security.auth.sdk;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
@Schema(description = "네이버 DTO")
public class NaversResponse implements OAuth2Response {

    private String id;
    private String name;
    private String email;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return id;
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
