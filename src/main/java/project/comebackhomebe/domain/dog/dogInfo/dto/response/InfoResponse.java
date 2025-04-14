package project.comebackhomebe.domain.dog.dogInfo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.dto.response.DogHealthResponse;
import project.comebackhomebe.domain.dog.dogImage.dto.response.ImageResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.util.List;
import java.util.stream.Collectors;

@Schema(title = "InfoResponse : 강아지 정보 반환 데이터")
public record InfoResponse(
        @Schema(description = "형태 타입", example = "LOST")
        Type type,
        Status status,
        Gender gender,
        String breed,
        String height,
        List<ImageResponse> imageResponses,
        DogHealthResponse healthResponses
) {
    public static InfoResponse of(Dog dog) {
        return new InfoResponse(
                dog.getType(),
                dog.getStatus(),
                dog.getGender(),
                dog.getBreed(),
                dog.getHeight(),
                ImageResponse.listOf(dog.getImageUrls()),
                DogHealthResponse.of(dog.getHealth())
        );
    }

    public static List<InfoResponse> listOf(List<Dog> dogs) {
        return dogs.stream()
                .map(InfoResponse::of)
                .collect(Collectors.toList());
    }
}
