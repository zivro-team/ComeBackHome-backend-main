package project.comebackhomebe.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 미리 서비스에 등록된 유저라면 카카오 id 찾아서 확인하고
    // 거기다가 토큰을 새로 발급만 해주면 되지 ㅇㅈ?
    Member findByVerifyKey(String verifyKey);
}
