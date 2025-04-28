package project.comebackhomebe.domain.dog.dogImage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "사용 X")
public record ImageRequest(
        MultipartFile multipartFile
) {
}
