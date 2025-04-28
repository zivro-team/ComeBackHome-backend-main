package project.comebackhomebe.domain.dog.dogHealth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "강아지 건강 상태 정보 Request")
public record DogHealthRequest(
        @Schema(description = "강아지 건강 상태 1")
        String health_status_1,
        @Schema(description = "강아지 건강 상태 2")
        String health_status_2,
        @Schema(description = "강아지 건강 상태 3")
        String health_status_3,
        @Schema(description = "강아지 특징")
        String feature
) {
}
