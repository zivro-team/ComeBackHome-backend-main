package project.comebackhomebe.global.security.auth.sdk;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
@Schema(description = "네이버 DTO")
public class NaversResponse implements OAuth2Response {

    @JsonProperty("response")
    private InnerNaverResponse response;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return response.getId();
    }

    @Override
    public String getEmail() {
        return response.getEmail();
    }

    @Override
    public String getName() {
        return response.getName();
    }

    @Data
    public static class InnerNaverResponse {
        private String id;
        private String name;
        private String email;
        private String nickname;
        private String profile_image;
        private String age;
        private String gender;
        private String birthday;
    }
}
