package project.comebackhomebe.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

/**
 * yml 파일이 아닌 클래스 파일로 저장할때 사용
 */
@Component
@RequiredArgsConstructor
public class SocialClientRegistration {

    @Value("${spring.google.redirect_uri}")
    private String redirectUri;

    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId("pefwgC6hlz43GbqHn2BB")
                .clientSecret("4UuXTNOB6z")
                .redirectUri("http://localhost:8085/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize") // 토큰 uri랑 user, auth 는 이렇게 생성할떄 필수임
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration googleClientRegistration() {

        return ClientRegistration.withRegistrationId("google")
                .clientId("416289344803-am06jjp5pv895q3u4imn7eth7t1pep0f.apps.googleusercontent.com")
                .clientSecret("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")
                .redirectUri(redirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }

    public ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId("3334a32d79042089a1ea0feb2369d322") // Kakao 개발자 콘솔에서 발급받은 클라이언트 ID
                .clientSecret("O0qeGm3rbOJH7H3t2PwrxrY4yhLeBnyP") // Kakao 개발자 콘솔에서 발급받은 클라이언트 시크릿 (선택적)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST) // 인증 방식
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 인증 코드 방식
                .redirectUri("{baseUrl}/login/oauth2/code/kakao") // 리다이렉트 URI
                .scope("profile_nickname", "account_email") // 요청할 스코프 (필요에 따라 조정)
                .authorizationUri("https://kauth.kakao.com/oauth/authorize") // Kakao 인증 URI
                .tokenUri("https://kauth.kakao.com/oauth/token") // Kakao 토큰 발급 URI
                .userInfoUri("https://kapi.kakao.com/v2/user/me") // 사용자 정보 조회 URI
                .userNameAttributeName("id") // 사용자 식별 속성 (Kakao는 "id" 사용)
                .clientName("Kakao") // 클라이언트 이름 (표시용)
                .build();
    }

}

