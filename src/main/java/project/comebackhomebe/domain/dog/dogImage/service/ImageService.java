package project.comebackhomebe.domain.dog.dogImage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    // 이미지 URL 저장
    List<String> saveImage(List<MultipartFile> file) throws IOException;

    // 이미지 가져오기
    List<String> getImage(Long dogId) throws IOException;

    // 이미지 수정

    // 이미지 삭제
    void deleteImage(List<String> imageUrl);
}
