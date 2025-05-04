package project.comebackhomebe.domain.dog.dogInfo.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.global.util.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type; // Lost, Discover

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // Found, Find

    private String name; // 실종견 이름

    private int age; // 실종견 나이

    private String breed; // 종

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    private int weight; // 무게

    private String height; // 크기

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "dog") // EAGER 시 오류
    @ToString.Exclude
    private List<Image> imageUrls = new ArrayList<>();

    @OneToOne(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private DogHealth health;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    public static Dog createDiscoverDogInfo(String breed, Gender gender, String height, List<Image> imageUrls, DogHealth dogHealth, Member member) {
        return Dog.builder()
                .type(Type.DISCOVER)
                .status(Status.FIND)
                .breed(breed)
                .gender(gender)
                .height(height)
                .imageUrls(imageUrls)
                .member(member)
                .health(dogHealth)
                .build();
    }

    public static Dog createLostDogInfo(String name, String breed, Gender gender, String height, int weight, List<Image> imageUrls, DogHealth dogHealth, Member member) {
        return Dog.builder()
                .type(Type.LOST)
                .status(Status.FIND)
                .name(name)
                .breed(breed)
                .gender(gender)
                .height(height)
                .weight(weight)
                .imageUrls(imageUrls)
                .member(member)
                .health(dogHealth)
                .build();
    }

    public static Dog foundDogInfo(Long id, Dog dog) {
        return Dog.builder()
                .id(id)
                .type(dog.type)
                .status(Status.FOUND)
                .breed(dog.breed)
                .gender(dog.gender)
                .height(dog.height)
                .imageUrls(dog.imageUrls)
                .health(dog.health)
                .build();
    }
}
