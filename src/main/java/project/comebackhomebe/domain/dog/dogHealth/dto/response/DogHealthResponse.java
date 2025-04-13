package project.comebackhomebe.domain.dog.dogHealth.dto.response;

import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;

import java.util.List;
import java.util.stream.Collectors;

public record DogHealthResponse(
        String health_status_1,
        String health_status_2,
        String health_status_3,
        String feature
) {
    public static DogHealthResponse of(DogHealth dogHealth) {
        return new DogHealthResponse(
                dogHealth.getHealth_status_1(),
                dogHealth.getHealth_status_2(),
                dogHealth.getHealth_status_3(),
                dogHealth.getFeature()
        );
    }

    public static List<DogHealthResponse> listOf(List<DogHealth> dogHealths) {
        return dogHealths.stream()
                .map(DogHealthResponse::of)
                .collect(Collectors.toList());
    }
}
