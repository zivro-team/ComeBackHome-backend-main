package project.comebackhomebe.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 멤버 테이블에서 해당 verifyKey와 동일한 멤버를 반환합니다.
     *
     * @param verifyKey : kakao 1234
     * @return
     */
    Member findByVerifyKey(String verifyKey);

    /**
     * 멤버 테이블에서 해당 verifyKey와 동일한 멤버에서
     * id만 반환합니다.
     *
     * @param verifyKey
     * @return
     */
    @Query("SELECT id FROM Member WHERE verifyKey = :verifyKey")
    Long findIdByVerifyKey(@Param("verifyKey") String verifyKey);
}
