package project.comebackhomebe.domain.dog.dogHealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "강아지 건강 상태 정보 Response")
public record DogHealthResponse(
        @Schema(description = "강아지 건강 상태 1")
        String health_status_1,
        @Schema(description = "강아지 건강 상태 2")
        String health_status_2,
        @Schema(description = "강아지 건강 상태 3")
        String health_status_3,
        @Schema(description = "강아지 특징")
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
