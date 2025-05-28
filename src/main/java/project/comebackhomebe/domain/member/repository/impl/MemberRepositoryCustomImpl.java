package project.comebackhomebe.domain.member.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogInfo.entity.QDog;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.QMember;
import project.comebackhomebe.domain.member.repository.MemberRepositoryCustom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Member DB에 있는 모든 멤버 값을 가져옵니다.
     *
     * @param pageable : page, size, sort
     * @return
     */
    @Override
    public Page<Member> getAllMembers(Pageable pageable) {
        QMember qMember = QMember.member;

        List<Member> members = jpaQueryFactory
                .selectFrom(qMember)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(members);
    }

    /**
     * 동일한 강아지 종을 등록한 사용자를 찾습니다.
     * Dog 테이블에서 같은 강아지 종을 먼저 거르고
     * 그 강아지를 등록한 멤버의 FCM 토큰을 가져와 반환합니다.
     *
     * @param breed : golden retriever
     * @return
     */
    @Override
    public List<String> getFcmTokensByBreed(String breed) {
        QDog qDog = QDog.dog;
        QMember qMember = QMember.member;

        return jpaQueryFactory
                .select(qMember.fcmToken)
                .from(qDog)
                .join(qDog.member, qMember)
                .where(qDog.breed.eq(breed))
                .distinct()  // 중복 제거
                .fetch();
    }

    /**
     * 동일한 지역을 등록한 사용자를 찾습니다.
     * Dog 테이블에서 강아지 위치를 먼저 거르고
     * 그 강아지를 등록한 멤버의 FCM 토큰을 가져와 반홥합니다.
     *
     * @param area : 경기도 성남시 수정구 ...
     * @return
     */
    @Override
    public List<String> getFcmTokensByArea(String area) {
        QDog qDog = QDog.dog;
        QMember qMember = QMember.member;

        return jpaQueryFactory
                .select(qMember.fcmToken)
                .from(qDog)
                .join(qDog.member, qMember)
                .where(qDog.area.eq(area))
                .distinct()  // 중복 제거
                .fetch();
    }

    /**
     * AI 분석 결과 나온 강아지 id를 List 형식으로 받은 뒤
     * 그 강아지 들을 등록한 멤버의 FCM 토큰을 가져와 반환합니다.
     *
     * @param dogIds
     * @return
     */
    @Override
    public List<String> getFcmTokensByDog(List<Long> dogIds) {
        QDog qDog = QDog.dog;
        QMember qMember = QMember.member;

        return jpaQueryFactory
                .select(qMember.fcmToken)
                .from(qDog)
                .join(qDog.member, qMember)
                .where(qDog.id.in(dogIds))
                .fetch();
    }
}
