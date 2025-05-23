package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

@Schema(title = "강아지 정보 입력 데이터 Request")
public record DogDiscoverInfoRequest(
        @Schema(description = "강아지 위치")
        String area,
        @Schema(description = "강아지 종")
        String breed,
        @Schema(description = "강아지 계열")
        String breedType,
        @Schema(description = "강아지 크기")
        String height,
        @Schema(description = "강아지 성별")
        Gender gender
) {
}
