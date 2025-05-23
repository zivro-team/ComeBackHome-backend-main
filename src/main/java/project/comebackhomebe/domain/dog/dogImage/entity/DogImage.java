package project.comebackhomebe.domain.dog.dogImage.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public static DogImage from(DogImageRequest imageRequest) {
        return DogImage.builder().imageUrl(String.valueOf(imageRequest)).build();
    }

    public static List<DogImage> listFrom(List<DogImageRequest> imageRequests) {
        return imageRequests.stream()
                .map(DogImage::from)
                .collect(Collectors.toList());
    }

}
