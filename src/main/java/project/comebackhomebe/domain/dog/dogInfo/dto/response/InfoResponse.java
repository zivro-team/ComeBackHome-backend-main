package project.comebackhomebe.domain.dog.dogInfo.dto.response;


import project.comebackhomebe.domain.dog.dogImage.entity.Image;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.util.List;

public record InfoResponse(
        Type type,
        Status status,
        Gender gender,
        String name,
        int age,
        String breed,
        String height,
        List<Image> imageUrls
) {
    public static InfoResponse of(Dog dog) {
        return new InfoResponse(
                dog.getType(),
                dog.getStatus(),
                dog.getGender(),
                dog.getName(),
                dog.getAge(),
                dog.getBreed(),
                dog.getHeight(),
                dog.getImageUrls()
        );
    }
}
