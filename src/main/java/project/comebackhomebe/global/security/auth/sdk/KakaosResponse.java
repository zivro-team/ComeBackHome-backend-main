package project.comebackhomebe.global.security.auth.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import project.comebackhomebe.global.security.auth.OAuth2Response;

@Getter
public class KakaosResponse implements OAuth2Response {

    private Long id;

    @JsonProperty("connected_at")
    private String connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class KakaoAccount {
        private String email;

        @JsonProperty("email_verified")
        private boolean emailVerified;

        private Profile profile;

        @Getter
        public static class Profile {
            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
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
        return kakaoAccount.getProfile().getNickname();
    }
}

