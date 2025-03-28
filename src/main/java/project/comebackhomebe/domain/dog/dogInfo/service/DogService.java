package project.comebackhomebe.domain.dog.dogInfo.service;

import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Gender;

import java.io.IOException;
import java.util.List;

public interface DogService {
    // 이미지 올리기 테스트
    InfoResponse createInfo(String breed, Gender gender, String height, List<MultipartFile> images) throws IOException;
}
