package project.comebackhomebe.domain.dog.dogInfo.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;

import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "Dog")
    @ToString.Exclude
    private List<Image> imageUrls; // 이미지들

    public static Dog createDiscover(String breed, Gender gender, String height, List<Image> imageUrls) {
        return Dog.builder()
                .type(Type.DISCOVER)
                .status(Status.FIND)
                .breed(breed)
                .gender(gender)
                .height(height)
                .imageUrls(imageUrls)
                .build();
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
}
