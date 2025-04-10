package project.comebackhomebe.domain.dog.dogInfo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

import java.io.IOException;
import java.util.List;

public interface DogService {
    // 정보 올리기
    InfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images) throws IOException;

    // 정보 가져오기
    InfoResponse getInfo(Long id) throws IOException;

    // 전체 정보 가져오기
    List<InfoResponse> getList(Pageable pageable);

}
