package project.comebackhomebe.domain.dog.dogImage.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogImage.service.ImageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Override
    public List<String> saveImage(List<MultipartFile> files) throws IOException {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();

            InputStream inputStream = file.getInputStream();

            BlobId blobId = BlobId.of(bucketName, fileName);

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, inputStream);

            String imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
            imageUrls.add(imageUrl);
        }

        return imageUrls;

    }

    @Override
    public void deleteImage(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            String fileName = extractFileNameFromUrl(imageUrl);
            BlobId blobId = BlobId.of(bucketName, fileName);
            storage.delete(blobId); // GCS에서 파일 삭제
        }

    }

    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
