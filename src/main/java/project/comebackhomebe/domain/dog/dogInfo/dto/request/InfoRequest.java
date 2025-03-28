package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

public record InfoRequest(
        String breed,
        String height,
        Gender gender
) {
}
