package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;

import java.util.List;

public record DogLostAllRequest(
    DogLostInfoRequest dogLostInfoRequest,
    DogHealthRequest dogHealthRequest,
    List<DogImageRequest> dogImageRequest
) {
}
