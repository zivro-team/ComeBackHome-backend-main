package project.comebackhomebe.domain.dog.dogHealth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    public static DogHealth from(String health_status_1, String health_status_2, String health_status_3, String feature) {
        return DogHealth.builder()
                .health_status_1(health_status_1)
                .health_status_2(health_status_2)
                .health_status_3(health_status_3)
                .feature(feature)
                .build();
    }
}
