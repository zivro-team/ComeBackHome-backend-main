package project.comebackhomebe.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

@Schema(title = "멤버 정보 반환 Response")
public record MemberInfo(
        @Schema(description = "사용자 고유 값")
        String verifyKey,
        @Schema(description = "사용자 이름")
        String username,
        @Schema(description = "사용자 이메일")
        String email,
        @Schema(description = "사용자 역할")
        Role role

) {
    /**
     * Entity -> DTO 연결 코드
     *
     * @param member : membero
     * @return
     */
    public static MemberInfo of(Member member) {
        return new MemberInfo(
                member.getVerifyKey(),
                member.getUsername(),
                member.getEmail(),
                member.getRole()
        );
    }

    /**
     * 일반 값 -> DTO 연결 코드
     *
     * @param verifyKey
     * @param username
     * @param email
     * @param role
     * @return
     */
    public static MemberInfo to(String verifyKey, String username, String email, Role role) {
        return new MemberInfo(
                verifyKey,
                username,
                email,
                role
        );
    }

    /**
     * 다수의 멤버를 List 형식으로 변환
     * List<Memebr> -> List<MemberInfo>
     *
     * @param members : List<Member>
     * @return
     */
    public static List<MemberInfo> listOf(List<Member> members) {
        return members.stream()
                .map(MemberInfo::of)
                .collect(Collectors.toList());
    }
}
