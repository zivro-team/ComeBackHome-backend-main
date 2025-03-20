package project.comebackhomebe.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String kakao_id;

    public static Member from (String username, Role role, String kakao_id) {
        return Member.builder()
                .username(username)
                .role(role)
                .kakao_id(kakao_id)
                .build();
    }
}
