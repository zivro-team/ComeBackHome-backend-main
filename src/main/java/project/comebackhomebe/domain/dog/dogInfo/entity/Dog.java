package project.comebackhomebe.domain.dog.dogInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type; // Lost, Found

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String name; // 실종견 이름

    private int age; // 실종견 나이

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int weight; //kg 단위로 해주세요

    // 이거 Enum 타입 할지 모르겠음
    // AI 쪽 파트랑 협의 필요
    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private int height;
}
