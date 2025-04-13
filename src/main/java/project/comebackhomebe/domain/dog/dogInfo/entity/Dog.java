package project.comebackhomebe.domain.dog.dogInfo.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;
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

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "dog")
    @ToString.Exclude
    private List<Image> imageUrls; // 이미지들

    @OneToOne(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private DogHealth health;

    public static Dog createDiscover(String breed, Gender gender, String height, List<Image> imageUrls, DogHealthRequest healthRequest) {
        Dog dog = Dog.builder()
                .type(Type.DISCOVER)
                .status(Status.FIND)
                .breed(breed)
                .gender(gender)
                .height(height)
                .imageUrls(new ArrayList<>())
                .build();

        imageUrls.forEach(dog::addImage);

        DogHealth dogHealth = DogHealth.from(healthRequest);
        dog.addHealth(dogHealth);
        return dog;
    }

    public static Dog createLost(String breed, Gender gender, String height, List<Image> imageUrls) {
        return Dog.builder()
                .type(Type.LOST)
                .status(Status.FIND)
                .breed(breed)
                .gender(gender)
                .height(height)
                .imageUrls(imageUrls)
                .build();
    }

    public static Dog updateDiscover(Dog dog) {
        return Dog.builder()
                .type(Type.DISCOVER)
                .status(Status.FOUND)
                .breed(dog.breed)
                .gender(dog.gender)
                .height(dog.height)
                .build();
    }

    public static Dog updateLost(Dog dog) {
        return Dog.builder()
                .type(Type.LOST)
                .status(Status.FOUND)
                .breed(dog.breed)
                .gender(dog.gender)
                .height(dog.height)
                .build();
    }

    public void addImage(Image image) {
        this.imageUrls.add(image);
        image.setDog(this); // Image의 dog 필드 설정
    }

    public void addHealth(DogHealth health) {
        this.health = health;
        health.setDog(this);
    }

}
