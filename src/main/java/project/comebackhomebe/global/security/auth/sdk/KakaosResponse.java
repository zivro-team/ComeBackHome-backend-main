package project.comebackhomebe.global.security.auth.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
@Schema(description = "카카오 DTO")
public class KakaosResponse implements OAuth2Response {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonProperty("properties")
    private Properties properties;

    // 내부 클래스 설계
    @Getter
    public static class KakaoAccount {
        private String email;
    }

    @Getter
    public static class Properties {
        private String nickname;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(id);
    }

    @Override
    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @Override
    public String getName() {
        return properties.getNickname();
    }
}

