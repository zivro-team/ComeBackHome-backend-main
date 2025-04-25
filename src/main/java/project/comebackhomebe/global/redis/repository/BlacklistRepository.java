package project.comebackhomebe.global.redis.repository;

import org.springframework.data.repository.CrudRepository;
import project.comebackhomebe.global.redis.domain.Blacklist;

public interface BlacklistRepository extends CrudRepository<Blacklist, String> {
}
