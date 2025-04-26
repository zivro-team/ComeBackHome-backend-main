package project.comebackhomebe.domain.dog.dogInfo.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.InfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.io.IOException;
import java.util.List;

public interface DogService {
    // 정보 올리기
    InfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images, DogHealthRequest request) throws IOException;

    // 정보 올리기 (이미지를 url 들로)
    InfoResponse createInfos(Type type, String breed, Gender gender, String height, @Valid List<DogImageRequest> imageUrls, DogHealthRequest request) throws IOException;

    // 정보 가져오기
    InfoResponse getInfo(Long id) throws IOException;

    // 전체 정보 가져오기
    List<InfoResponse> getList(Pageable pageable);

    // 찾음 처리
    InfoResponse updateInfo(Long id) throws IOException;

    // 종 별로 정보 가져오기
    List<InfoResponse> getListByBreed(String breed, Pageable pageable);

    // 신고, 잃어버림 기준 가져오기
    List<InfoResponse> getListByType(Type type, Pageable pageable);

}
