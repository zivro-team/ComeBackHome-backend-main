package project.comebackhomebe.global.redis.repository;

import org.springframework.data.repository.CrudRepository;
import project.comebackhomebe.global.redis.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByVerifyKey(String verifyKey);

    Optional<RefreshToken> findById(String refreshToken);

}
