package project.comebackhomebe.domain.dog.dogInfo.dto.response;

import project.comebackhomebe.domain.dog.dogImage.dto.response.ImageResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.util.List;
import java.util.stream.Collectors;

public record InfoResponse(
        Type type,
        Status status,
        Gender gender,
        String breed,
        String height,
        List<ImageResponse> imageResponses
) {
    public static InfoResponse of(Dog dog) {
        return new InfoResponse(
                dog.getType(),
                dog.getStatus(),
                dog.getGender(),
                dog.getBreed(),
                dog.getHeight(),
                ImageResponse.listOf(dog.getImageUrls())
        );
    }

    public static List<InfoResponse> listOf(List<Dog> dogs) {
        return dogs.stream()
                .map(InfoResponse::of)
                .collect(Collectors.toList());
    }
}
