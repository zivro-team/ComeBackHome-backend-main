package project.comebackhomebe.domain.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Member")
@Schema(name = "유저 정보", description = "유저 정보를 기록하는 엔티티입니다.")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, name = "verifyKey")
    private String verifyKey;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member from(String verifyKey, String username, String email, Role role) {
        return Member.builder()
                .verifyKey(verifyKey)
                .username(username)
                .email(email)
                .role(role)
                .build();
    }
}
