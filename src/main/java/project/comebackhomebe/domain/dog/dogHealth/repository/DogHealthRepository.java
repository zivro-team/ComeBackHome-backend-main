package project.comebackhomebe.domain.dog.dogHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;

@Repository
public interface DogHealthRepository extends JpaRepository<DogHealth, Long> {
}
