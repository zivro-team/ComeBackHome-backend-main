package project.comebackhomebe.domain.dog.dogImage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(title = "강아지 이미지 가져오기 Request")
public record DogImageRequest(
        @Schema(description = "강아지 이미지 URL")
        String imageUrl
) {
}
