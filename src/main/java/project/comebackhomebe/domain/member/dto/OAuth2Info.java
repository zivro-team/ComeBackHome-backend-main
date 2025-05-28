package project.comebackhomebe.domain.member.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import project.comebackhomebe.domain.member.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2Info implements OAuth2User {
    private final MemberInfo memberInfo;

    /**
     * 소셜 로그인 중 해당 데이터에서 직렬화 생성
     *
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "username", memberInfo.username(),
                "email", memberInfo.email()
        );
    }

    /**
     * 추후 사용 할 수 있음
     * 아직 사용하지 않습니다.
     *
     * @return : null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 유저 이름 추출
     *
     * @return
     */
    @Override
    public String getName() {
        return memberInfo.username();
    }

    /**
     * 소셜 + 유저 이름 추출
     *
     * @return
     */
    public String getVerifyKey() {
        return memberInfo.verifyKey();
    }

    /**
     * 유저 이메일 추출
     *
     * @return
     */
    public String getEmail() {
        return memberInfo.email();
    }

    /**
     * 유저 권한 추출
     *
     * @return
     */
    public Role getRole() {
        return memberInfo.role();
    }
}
