package project.comebackhomebe.domain.dog.dogInfo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.service.DogHealthService;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;
import project.comebackhomebe.domain.dog.dogImage.service.ImageService;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
    private final DogRepositoryCustom dogRepositoryCustom;
    private final ImageService imageService;
    private final DogHealthService dogHealthService;

    @Override
    public InfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images, DogHealthRequest healthRequest) throws IOException {
        List<String> imageUrls = imageService.saveImage(images);

        // URL 리스트를 Image 엔티티 리스트로 변환
        List<Image> imageEntities = imageUrls.stream()
                .map(Image::from) // Image 생성자 사용
                .collect(Collectors.toList());

        Dog dog = Dog.createDiscover(breed, gender, height, imageEntities, healthRequest);

        dogRepository.save(dog);

        return InfoResponse.of(dog);
    }

    @Override
    public InfoResponse getInfo(String breed, Gender gender, String height, List<DogImageRequest> images, DogHealthRequest healthRequest) throws IOException {
        List<String> imageUrls = images.stream()
                .map(DogImageRequest::imageUrl)
                .toList();

        List<Image> imageEntities = imageUrls.stream()
                .map(Image::from) // Image 생성자 사용
                .collect(Collectors.toList());

        Dog dog = Dog.createDiscover(breed, gender, height, imageEntities, healthRequest);

        dogRepository.save(dog);

        return InfoResponse.of(dog);
    }

    @Override
    public InfoResponse getInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        return InfoResponse.of(dog);
    }

    @Override
    public List<InfoResponse> getList(Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getAllDogInfo(pageable);

        List<Dog> infoResponses = dogs.getContent();

        return InfoResponse.listOf(infoResponses);
    }


}
