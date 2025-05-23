package project.comebackhomebe.domain.dog.dogHealth.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String health_status_1;

    private String health_status_2;

    private String health_status_3;

    private String feature;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public static DogHealth from(DogHealthRequest request) {
        return DogHealth.builder()
                .health_status_1(request.health_status_1())
                .health_status_2(request.health_status_2())
                .health_status_3(request.health_status_3())
                .feature(request.feature())
                .build();
    }
}
