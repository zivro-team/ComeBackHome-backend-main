package project.comebackhomebe.domain.dog.dogInfo.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogDiscoverInfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogLostInfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogCommonResponse;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogDiscoverInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogLostInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

import java.io.IOException;
import java.util.List;

public interface DogService {
    // 정보 올리기 (이미지를 url 들로)
    DogLostInfoResponse createLostInfo(DogLostInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException, FirebaseMessagingException;

    // 정보 올리기
    DogDiscoverInfoResponse createDiscoverInfo(DogDiscoverInfoRequest infoRequest, List<DogImageRequest> imageRequest, DogHealthRequest healthRequest, HttpServletRequest request) throws IOException, FirebaseMessagingException;

    // 정보 가져오기
    DogCommonResponse getInfo(Long id) throws IOException;

    // 전체 정보 가져오기
    List<DogDiscoverInfoResponse> getDiscoverList(Pageable pageable);

    // 전체 정보 가져오기
    List<DogLostInfoResponse> getLostList(Pageable pageable);

    // 찾음 처리
    DogDiscoverInfoResponse foundInfo(Long id) throws IOException;

    // 종 별로 정보 가져오기
    List<DogDiscoverInfoResponse> getListByBreed(String breed, Pageable pageable);

    // 신고, 잃어버림 기준 가져오기
    List<DogDiscoverInfoResponse> getListByType(Type type, Pageable pageable);

    // 강아지 정보 삭제
    void deleteInfo(Long id) throws IOException;

}
