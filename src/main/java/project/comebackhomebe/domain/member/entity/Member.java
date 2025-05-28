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

    /**
     * 회원 고유 ID
     * 예시 : 123
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    /**
     * 소셜 별 회원 고유 값
     * 예시 : kakao 12345, naver 12345
     */
    @Column(nullable = false, name = "verifyKey")
    private String verifyKey;

    /**
     * 사용자 이름
     * 예시 : 임현성
     */
    @Column(nullable = false, name = "username")
    private String username;

    /**
     * 사용자 이메일
     * 예시 : jkl020627@gachon.ac.kr
     */
    @Column(nullable = false, name = "email")
    private String email;

    /**
     * 회원 권한
     * 예시 : user, admin, super_admin
     */
    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * 알림 토큰
     * 예시 : ejlkqjekk1l2341k313
     */
    @Column(nullable = true, name = "fcmToken")
    private String fcmToken;

    /**
     * 상태 표시
     * 예시 : 오프라인, 온라인
     */
    @Column(nullable = true, name = "status")
    private UserStatus status;

    /**
     * 강아지 테이블과 1 대 N 관계
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dog_id")
    @ToString.Exclude
    private List<Dog> dogs = new ArrayList<>();

    /**
     * 멤버 생성 빌더 패턴
     *
     * @param verifyKey
     * @param username
     * @param email
     * @param role
     * @return
     */
    public static Member from(String verifyKey, String username, String email, Role role) {
        return Member.builder()
                .verifyKey(verifyKey)
                .username(username)
                .email(email)
                .role(role)
                .status(UserStatus.ONLINE)
                .build();
    }

}
