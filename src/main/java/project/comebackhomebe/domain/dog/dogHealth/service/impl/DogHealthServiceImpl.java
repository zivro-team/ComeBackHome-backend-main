package project.comebackhomebe.domain.dog.dogHealth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.dto.response.DogHealthResponse;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;
import project.comebackhomebe.domain.dog.dogHealth.repository.DogHealthRepository;
import project.comebackhomebe.domain.dog.dogHealth.service.DogHealthService;

@Service
@RequiredArgsConstructor
public class DogHealthServiceImpl implements DogHealthService {

    private final DogHealthRepository dogHealthRepository;

    @Override
    public DogHealthResponse createDogHealth(DogHealthRequest dogHealthRequest) {

        DogHealth dogHealth = DogHealth.from(dogHealthRequest);

        return DogHealthResponse.of(dogHealth);
    }

    @Override
    public DogHealthResponse getDogHealth(Long dog_id) {
        DogHealth dogHealth = dogHealthRepository.findById(dog_id).orElse(null);

        return DogHealthResponse.of(dogHealth);
    }

    @Override
    public void deleteDogHealth(Long dog_id) {
        dogHealthRepository.deleteById(dog_id);
    }
}
