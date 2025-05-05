package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

@Schema(title = "강아지 정보 입력 데이터 Request")
public record DogLostInfoRequest(
        @Schema(description = "강아지 이름")
        String name,
        @Schema(description = "강아지 종")
        String breed,
        @Schema(description = "강아지 크기")
        String height,
        @Schema(description = "강아지 무게")
        int weight,
        @Schema(description = "강아지 성별")
        Gender gender,
        @Schema(description = "강아지 건강 상태 정보")
        DogHealthRequest dogHealthRequest
) {
}
