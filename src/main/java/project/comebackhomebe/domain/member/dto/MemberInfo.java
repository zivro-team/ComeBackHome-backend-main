package project.comebackhomebe.domain.member.dto;

import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;

public record MemberInfo(
        String username,
        Role role,
        String kakao_id

) {
    public static MemberInfo of(Member member) {
        return new MemberInfo(
                member.getUsername(),
                member.getRole(),
                member.getKakao_id()
        );
    }
}
