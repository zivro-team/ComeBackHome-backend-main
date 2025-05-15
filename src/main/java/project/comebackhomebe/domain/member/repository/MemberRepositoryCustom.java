package project.comebackhomebe.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    // 관리자
    Page<Member> getAllMembers(Pageable pageable);

    // 강아지 정보에서 멤버 정보 가져오기
    List<String> getFcmTokensByBreed(String breed);
}
