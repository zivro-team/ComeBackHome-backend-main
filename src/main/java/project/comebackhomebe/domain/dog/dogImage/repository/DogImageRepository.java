package project.comebackhomebe.domain.dog.dogImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.comebackhomebe.domain.dog.dogImage.entity.Image;

public interface DogImageRepository extends JpaRepository<Image, Long> {

}
