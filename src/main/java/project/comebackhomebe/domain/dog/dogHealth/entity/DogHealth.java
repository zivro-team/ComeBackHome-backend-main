package project.comebackhomebe.domain.dog.dogHealth.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public static DogHealth from(String health_status_1, String health_status_2, String health_status_3, String feature) {
        return DogHealth.builder()
                .health_status_1(health_status_1)
                .health_status_2(health_status_2)
                .health_status_3(health_status_3)
                .feature(feature)
                .build();
    }
}
