package project.comebackhomebe.domain.dog.dogInfo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;
import project.comebackhomebe.domain.dog.dogImage.service.ImageService;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
    private final ImageService imageService;

    @Override
    public InfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images) throws IOException {
        List<String> imageUrls = imageService.saveImage(images);

        // URL 리스트를 Image 엔티티 리스트로 변환
        List<Image> imageEntities = imageUrls.stream()
                .map(Image::from) // Image 생성자 사용
                .collect(Collectors.toList());

        Dog dog = Dog.createDiscover(breed, gender, height, imageEntities);

        dogRepository.save(dog);

        return InfoResponse.of(dog);
    }

    @Override
    public InfoResponse getInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        return InfoResponse.of(dog);
    }

    @Override
    public Page<InfoResponse> getList(Pageable pageable) {



        return null;
    }


}
