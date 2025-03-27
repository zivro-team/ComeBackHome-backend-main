package project.comebackhomebe.domain.dog.dogImage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    private String id;

    private String imageUrl;

    public static Image from (String imageUrl) {
        return Image.builder().imageUrl(imageUrl).build();
    }
}
