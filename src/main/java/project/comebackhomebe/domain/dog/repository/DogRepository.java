package project.comebackhomebe.domain.dog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.entity.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
