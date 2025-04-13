package project.comebackhomebe.domain.dog.dogHealth.service;

import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.dto.response.DogHealthResponse;

public interface DogHealthService {
    DogHealthResponse createDogHealth(DogHealthRequest dogHealthRequest);

    DogHealthResponse getDogHealth(Long dog_id);

    void deleteDogHealth(Long dog_id);


}
