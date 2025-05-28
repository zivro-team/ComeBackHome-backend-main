package project.comebackhomebe.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.comebackhomebe.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    // 관리자
    Page<Member> getAllMembers(Pageable pageable);

    // 종에서 fcmToken 가져오기
    List<String> getFcmTokensByBreed(String breed);

    // 지역에서 fcmToken 가져오기
    List<String> getFcmTokensByArea(String area);

    // AI 종 분석 후 알림
    List<String> getFcmTokensByDog(List<Long> dogIds);
}
