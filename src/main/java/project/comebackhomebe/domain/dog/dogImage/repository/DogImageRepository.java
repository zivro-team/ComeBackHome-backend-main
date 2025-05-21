package project.comebackhomebe.domain.dog.dogImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogImage.entity.DogImage;

@Repository
public interface DogImageRepository extends JpaRepository<DogImage, Long> {

}
