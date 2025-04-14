package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

@Schema(title = "InfoRequest : 강아지 정보 입력 데이터")
public record InfoRequest(
        String breed,
        String height,
        Gender gender,
        DogHealthRequest dogHealthRequest
) {
}
