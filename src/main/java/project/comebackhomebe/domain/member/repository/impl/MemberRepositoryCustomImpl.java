package project.comebackhomebe.domain.member.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.QDog;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.entity.QMember;
import project.comebackhomebe.domain.member.repository.MemberRepositoryCustom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

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

    @Override
    public List<String> getMembersByDog(Dog dog) {
        QMember qMember = QMember.member;
        QDog qDog = QDog.dog;

        return jpaQueryFactory
                .select(qMember.fcmToken)
                .from(qMember.dogs, qDog)
                .where(qDog.id.eq(dog.getId()))
                .fetch();
    }
}
