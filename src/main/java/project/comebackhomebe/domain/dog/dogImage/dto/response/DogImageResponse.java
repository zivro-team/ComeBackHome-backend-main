package project.comebackhomebe.domain.dog.dogImage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogImage.entity.DogImage;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "강아지 사진 응답 Response")
public record DogImageResponse(
        @Schema(description = "강아지 이미지 URL")
        String imageUrl
) {
    public static DogImageResponse of(DogImage image) {
        return new DogImageResponse(image.getImageUrl());
    }

    public static List<DogImageResponse> listOf(List<DogImage> images) {
        return images.stream()
                .map(DogImageResponse::of)
                .collect(Collectors.toList());
    }
}
