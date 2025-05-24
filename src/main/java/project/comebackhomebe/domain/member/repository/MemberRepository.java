package project.comebackhomebe.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByVerifyKey(String verifyKey);

    @Query("SELECT id FROM Member WHERE verifyKey = :verifyKey")
    Long findIdByVerifyKey(@Param("verifyKey")String verifyKey);
}
