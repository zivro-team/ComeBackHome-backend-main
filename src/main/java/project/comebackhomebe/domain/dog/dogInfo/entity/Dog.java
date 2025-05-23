package project.comebackhomebe.domain.dog.dogInfo.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;
import project.comebackhomebe.domain.dog.dogImage.entity.DogImage;
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

    @Column(nullable = true)
    private String name; // 실종견 이름

    @Column(nullable = true)
    private int age; // 실종견 나이

    @Column(nullable = false)
    private String breed; // 종

    @Column(nullable = false)
    private String middle_breed; // 중분류 종

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    @Column(nullable = true)
    private int weight; // 무게

    @Column(nullable = false)
    private String height; // 크기

    @Column(nullable = false)
    private String area; // 도로명 위치

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "dog") // EAGER 시 오류
    @ToString.Exclude
    private List<DogImage> imageUrls = new ArrayList<>();

    @OneToOne(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private DogHealth health;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    public static Dog createDiscoverDogInfo(String area, String breed, Gender gender, String height, List<DogImage> images, DogHealth health, Member member) {
        Dog dog = Dog.builder()
                .type(Type.DISCOVER)
                .status(Status.FIND)
                .area(area)
                .breed(breed)
                .gender(gender)
                .height(height)
                .member(member)
                .build();

        for (DogImage image : images) {
            dog.addImage(image); // 연관관계 설정
        }

        dog.setHealth(health); // 연관관계 설정

        return dog;
    }

    public static Dog createLostDogInfo(String area, String name, String breed, Gender gender, String height, int weight, List<DogImage> images, DogHealth health, Member member) {
        Dog dog = Dog.builder()
                .type(Type.LOST)
                .status(Status.FIND)
                .area(area)
                .name(name)
                .breed(breed)
                .gender(gender)
                .height(height)
                .weight(weight)
                .member(member)
                .build();

        for (DogImage image : images) {
            dog.addImage(image); // 연관관계 설정
        }

        dog.setHealth(health); // 연관관계 설정

        return dog;
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

    public void addImage(DogImage image) {
        imageUrls.add(image);
        image.setDog(this);
    }

    public void setHealth(DogHealth health) {
        this.health = health;
        health.setDog(this);
    }
}
