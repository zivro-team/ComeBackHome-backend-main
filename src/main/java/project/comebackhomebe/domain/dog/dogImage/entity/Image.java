package project.comebackhomebe.domain.dog.dogImage.entity;

import jakarta.persistence.*;
import lombok.*;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public static Image from (String imageUrl) {
        return Image.builder().imageUrl(imageUrl).build();
    }
}
