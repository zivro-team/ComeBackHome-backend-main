package project.comebackhomebe.domain.dog.dogInfo.service.impl;

import com.google.firebase.messaging.FirebaseMessagingException;
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
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogDiscoverInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogLostInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.domain.notification.service.NotificationService;
import project.comebackhomebe.global.security.jwt.JwtService;
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
    private final JwtService jwtService;
    private final NotificationService notificationService;

    @Override
    public DogLostInfoResponse createLostInfo(DogLostInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException, FirebaseMessagingException {
        String token = jwtService.resolveAccessToken(request);

        String verifyKey = jwtUtil.getVerifyKey(token);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        List<DogImage> images = DogImage.listFrom(imageRequest);

        DogHealth health = DogHealth.from(healthRequest);

        Dog dog = Dog.createLostDogInfo(
                infoRequest.area(),
                infoRequest.name(),
                infoRequest.breed(),
                infoRequest.gender(),
                infoRequest.height(),
                infoRequest.weight(),
                images,
                health,
                member
        );

        notificationService.findDogByBoundary(infoRequest.area());

        dogRepository.save(dog);

        return DogLostInfoResponse.of(dog);
    }

    @Override
    public DogDiscoverInfoResponse createDiscoverInfo(DogDiscoverInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException, FirebaseMessagingException {
        String token = jwtService.resolveAccessToken(request);

        String verifyKey = jwtUtil.getVerifyKey(token);

        Member member = memberRepository.findByVerifyKey(verifyKey);

        List<DogImage> images = DogImage.listFrom(imageRequest);

        DogHealth health = DogHealth.from(healthRequest);

        Dog dog = Dog.createDiscoverDogInfo(
                infoRequest.area(),
                infoRequest.breed(),
                infoRequest.gender(),
                infoRequest.height(),
                images,
                health,
                member
        );

        notificationService.registerNewDogFromBreed(infoRequest.breed());
        notificationService.findDogByBoundary(infoRequest.area());

        dogRepository.save(dog);

        return DogDiscoverInfoResponse.of(dog);
    }

    @Override
    public DogDiscoverInfoResponse getInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        return DogDiscoverInfoResponse.of(dog);
    }

    @Override
    public DogDiscoverInfoResponse foundInfo(Long id) throws IOException {
        Dog dog = dogRepository.getByIdOrElseThrow(id);

        Dog updateDog = Dog.foundDogInfo(id, dog);

        dogRepository.save(updateDog);

        return DogDiscoverInfoResponse.of(updateDog);
    }

    @Override
    public List<DogDiscoverInfoResponse> getListByBreed(String breed, Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByBreed(breed, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogDiscoverInfoResponse.listOf(infoResponses);
    }

    @Override
    public List<DogDiscoverInfoResponse> getListByType(Type type, Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByType(type, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogDiscoverInfoResponse.listOf(infoResponses);
    }

    @Override
    public void deleteInfo(Long id) throws IOException {
        dogRepository.deleteById(id);
    }

    @Override
    public List<DogDiscoverInfoResponse> getDiscoverList(Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByType(Type.DISCOVER, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogDiscoverInfoResponse.listOf(infoResponses);
    }

    @Override
    public List<DogLostInfoResponse> getLostList(Pageable pageable) {
        Slice<Dog> dogs = dogRepositoryCustom.getDogInfoByType(Type.LOST, pageable);

        List<Dog> infoResponses = dogs.getContent();

        return DogLostInfoResponse.listOf(infoResponses);
    }

}
