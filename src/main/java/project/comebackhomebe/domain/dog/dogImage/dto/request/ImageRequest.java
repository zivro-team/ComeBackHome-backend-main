package project.comebackhomebe.domain.dog.dogImage.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(
        MultipartFile multipartFile
) {
}
