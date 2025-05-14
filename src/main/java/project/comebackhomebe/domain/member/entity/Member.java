package project.comebackhomebe.domain.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = true, name = "fcmToken")
    private String fcmToken;

    @Column(nullable = false, name = "status")
    private UserStatus status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dog_id")
    @ToString.Exclude
    private List<Dog> dogs = new ArrayList<>();

    public static Member from(String verifyKey, String username, String email, Role role) {
        return Member.builder()
                .verifyKey(verifyKey)
                .username(username)
                .email(email)
                .role(role)
                .status(UserStatus.ONLINE)
                .build();
    }

    public static Member fcmTokenFrom(Member member, String fcmToken) {
        return Member.builder()
                .verifyKey(member.verifyKey)
                .username(member.username)
                .email(member.email)
                .role(member.role)
                .status(member.status)
                .fcmToken(fcmToken)
                .build();
    }
}
