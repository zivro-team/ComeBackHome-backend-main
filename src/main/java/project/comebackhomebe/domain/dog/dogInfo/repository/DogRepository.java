package project.comebackhomebe.domain.dog.dogInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    default Dog getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow();
    }
}
