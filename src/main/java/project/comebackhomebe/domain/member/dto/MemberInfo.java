package project.comebackhomebe.domain.member.dto;

import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;

public record MemberInfo(
        String verifyKey,
        String username,
        String email,
        Role role

) {
    public static MemberInfo of(Member member) {
        return new MemberInfo(
                member.getVerifyKey(),
                member.getUsername(),
                member.getEmail(),
                member.getRole()
        );
    }

    public static MemberInfo to(String verifyKey, String username, String email, Role role) {
        return new MemberInfo(
                verifyKey,
                username,
                email,
                role
        );
    }
}
