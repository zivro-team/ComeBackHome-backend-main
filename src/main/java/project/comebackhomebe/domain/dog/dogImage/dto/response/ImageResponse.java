package project.comebackhomebe.domain.dog.dogImage.dto.response;

import project.comebackhomebe.domain.dog.dogImage.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

public record ImageResponse(
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
