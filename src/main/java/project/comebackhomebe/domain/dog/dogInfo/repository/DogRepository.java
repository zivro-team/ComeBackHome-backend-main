package project.comebackhomebe.domain.dog.dogInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.exception.DogException;
import project.comebackhomebe.global.exception.ErrorCode;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    default Dog getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new DogException(ErrorCode.DOG_NOT_FOUND));
    }
}
