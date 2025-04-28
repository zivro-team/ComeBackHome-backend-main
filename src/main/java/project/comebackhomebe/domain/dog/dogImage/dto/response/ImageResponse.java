package project.comebackhomebe.domain.dog.dogImage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "강아지 사진 응답 Response")
public record ImageResponse(
        @Schema(description = "강아지 이미지 URL")
        String imageUrl
) {
    public static ImageResponse of(Image image) {
        return new ImageResponse(image.getImageUrl());
    }

    public static List<ImageResponse> listOf(List<Image> images) {
        return images.stream()
                .map(ImageResponse::of)
                .collect(Collectors.toList());
    }
}
