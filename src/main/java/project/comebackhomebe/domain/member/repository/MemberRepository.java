package project.comebackhomebe.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByVerifyKey(String verifyKey);

    // 없어도 됨
//    @Query("SELECT m.fcmToken FROM Dog d JOIN d.member m WHERE d.id = :id")
//    String findFcmTokenByDogId(@Param("id") Long dogId);
}
