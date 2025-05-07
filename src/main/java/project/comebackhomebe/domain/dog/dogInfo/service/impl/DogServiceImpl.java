package project.comebackhomebe.domain.dog.dogInfo.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogImage.entity.DogImage;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogDiscoverInfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogLostInfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.global.security.jwt.JwtUtil;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
    private final DogRepositoryCustom dogRepositoryCustom;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public DogInfoResponse createLostInfo(DogLostInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException {
        String token = jwtUtil.resolveToken(request);

        String verifyKey = jwtUtil.getVerifyKey(token);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        List<DogImage> images = DogImage.listFrom(imageRequest);

        DogHealth health = DogHealth.from(healthRequest);

        Dog dog = Dog.createLostDogInfo(
                infoRequest.name(),
                infoRequest.breed(),
                infoRequest.gender(),
                infoRequest.height(),
                infoRequest.weight(),
                images,
                health,
                member
        );


        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse createDiscoverInfo(DogDiscoverInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException {
        String token = jwtUtil.resolveToken(request);

        String verifyKey = jwtUtil.getVerifyKey(token);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        List<DogImage> images = DogImage.listFrom(imageRequest);

        DogHealth health = DogHealth.from(healthRequest);

        Dog dog = Dog.createDiscoverDogInfo(
                infoRequest.breed(),
                infoRequest.gender(),
                infoRequest.height(),
                images,
                health,
                member
        );


        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse getInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        return DogInfoResponse.of(dog);
    }

    @Override
    public DogInfoResponse foundInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        Dog updateDog = Dog.foundDogInfo(id, dog);

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
