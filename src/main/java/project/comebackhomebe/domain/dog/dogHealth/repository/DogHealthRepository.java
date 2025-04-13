package project.comebackhomebe.domain.dog.dogHealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.comebackhomebe.domain.dog.dogHealth.entity.DogHealth;

public interface DogHealthRepository extends JpaRepository<DogHealth, Long> {
}
