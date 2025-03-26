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

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "username", memberInfo.username(),
                "email", memberInfo.email()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() { return memberInfo.username(); }

    public String getVerifyKey() { return memberInfo.verifyKey(); }

    public String getEmail(){ return memberInfo.email(); }

    public Role getRole() {
        return memberInfo.role();
    }
}
