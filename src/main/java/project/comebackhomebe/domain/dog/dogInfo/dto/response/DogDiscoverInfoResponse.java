package project.comebackhomebe.domain.dog.dogInfo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import project.comebackhomebe.domain.dog.dogHealth.dto.response.DogHealthResponse;
import project.comebackhomebe.domain.dog.dogImage.dto.response.DogImageResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.util.List;
import java.util.stream.Collectors;

@Schema(title = "InfoResponse : 강아지 정보 반환 데이터")
public record DogDiscoverInfoResponse(
        @Schema(description = "형태 타입", example = "LOST, DISCOVER")
        Long id,
        @Schema(description = "형태 타입", example = "LOST, DISCOVER")
        Type type,
        @Schema(description = "강아지 현재 상태 (찾는 중)")
        Status status,
        @Schema(description = "강아지 성별")
        Gender gender,
        @Schema(description = "강아지 종")
        String breed,
        @Schema(description = "강아지 큰 종")
        String breedType,
        @Schema(description = "강아지 크기")
        String height,
        @Schema(description = "강아지 위치")
        String area,
        @Schema(description = "강아지 이미지들")
        List<DogImageResponse> imageResponses,
        @Schema(description = "강아지 건강 상태 정보")
        DogHealthResponse healthResponses
) {
    public static DogDiscoverInfoResponse of(Dog dog) {
        return new DogDiscoverInfoResponse(
                dog.getId(),
                dog.getType(),
                dog.getStatus(),
                dog.getGender(),
                dog.getBreed(),
                dog.getMiddleBreed(),
                dog.getHeight(),
                dog.getArea(),
                DogImageResponse.listOf(dog.getImageUrls()),
                DogHealthResponse.of(dog.getHealth())
        );
    }

    public static List<DogDiscoverInfoResponse> listOf(List<Dog> dogs) {
        return dogs.stream()
                .map(DogDiscoverInfoResponse::of)
                .collect(Collectors.toList());
    }
}
