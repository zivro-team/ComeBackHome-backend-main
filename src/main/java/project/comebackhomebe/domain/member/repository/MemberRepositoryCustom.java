package project.comebackhomebe.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.comebackhomebe.domain.member.entity.Member;

public interface MemberRepositoryCustom {
    // 관리자
    Page<Member> getAllMembers(Pageable pageable);
}
