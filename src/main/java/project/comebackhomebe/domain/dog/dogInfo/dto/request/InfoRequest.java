package project.comebackhomebe.domain.dog.dogInfo.dto.request;

import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

import java.util.List;

public record InfoRequest(
        String breed,
        String height,
        List<MultipartFile> images
) {
}
