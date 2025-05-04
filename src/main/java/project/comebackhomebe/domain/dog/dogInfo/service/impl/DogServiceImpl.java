package project.comebackhomebe.domain.dog.dogInfo.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;
import project.comebackhomebe.domain.dog.dogImage.service.ImageService;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.jwt.JwtUtil;

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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public DogInfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images, DogHealthRequest healthRequest) throws IOException {
        List<String> imageUrls = imageService.saveImage(images);

        // URL 리스트를 Image 엔티티 리스트로 변환
        List<Image> imageEntities = imageUrls.stream()
                .map(Image::from) // Image 생성자 사용
                .collect(Collectors.toList());

        Dog dog = Dog.createDiscover(breed, gender, height, imageEntities, healthRequest);

        dogRepository.save(dog);

        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse createInfos(Type type, String breed, Gender gender, String height, List<DogImageRequest> images, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException {
        List<String> imageUrls = images.stream()
                .map(DogImageRequest::imageUrl)
                .toList();

        List<Image> imageEntities = imageUrls.stream()
                .map(Image::from) // Image 생성자 사용
                .collect(Collectors.toList());

        String token = jwtUtil.resolveToken(request);

        String verifyKey = jwtUtil.getVerifyKey(token);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        Dog dog = Dog.createDogInfo(type, breed, gender, height, imageEntities, healthRequest, member);

        dogRepository.save(dog);

        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse getInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse updateInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        Dog updateDog = Dog.updateDogInfo(id, dog);

        dogRepository.save(updateDog);

        return DogInfoResponse.of(updateDog);
    }

    @Override
    public List<DogInfoResponse> getListByBreed(String breed, Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByBreed(breed, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogInfoResponse.listOf(infoResponses);
    }

    @Override
    public List<DogInfoResponse> getListByType(Type type, Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByType(type, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogInfoResponse.listOf(infoResponses);
    }

    @Override
    public void deleteInfo(Long id) throws IOException {
        dogRepository.deleteById(id);
    }

    @Override
    public List<DogInfoResponse> getList(Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getAllDogInfo(pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogInfoResponse.listOf(infoResponses);
    }

}
