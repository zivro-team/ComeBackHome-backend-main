package project.comebackhomebe.domain.member.dto;

import project.comebackhomebe.domain.member.entity.Role;

public record MemberInfo(
        String username,
        Role role

) {
}
