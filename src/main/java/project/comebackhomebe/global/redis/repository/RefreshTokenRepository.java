package project.comebackhomebe.global.redis.repository;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.global.redis.domain.RefreshToken;

@Repository
@EnableRedisRepositories(basePackageClasses = RefreshTokenRepository.class)
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByVerifyKey(String verifyKey);

    RefreshToken findByRefreshToken(String refreshToken);
}
