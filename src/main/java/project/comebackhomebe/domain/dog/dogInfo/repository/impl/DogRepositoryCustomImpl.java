package project.comebackhomebe.domain.dog.dogInfo.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.QDog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Status;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DogRepositoryCustomImpl implements DogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 다음 페이지가 있는지 여부 확인
    public static <T> boolean checkHasNext(List<T> items, Pageable pageable) {
        boolean hasNext = false;

        if (items.size() > pageable.getPageSize()) {
            items.remove(items.size() - 1); // 마지막 요소 제거
            hasNext = true;
        }

        return hasNext;
    }

    @Override
    public Slice<Dog> getAllDogInfo(Pageable pageable) {
        QDog dog = QDog.dog;

        List<Dog> dogs = jpaQueryFactory
                .selectFrom(dog)
                .orderBy(dog.createdDate.desc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();


        return new SliceImpl<>(dogs, pageable, checkHasNext(dogs, pageable));
    }

    @Override
    public Page<Dog> getDogInfoByType(Type type, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Dog> getDogInfoByStatus(Status status, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Dog> getDogInfoByBreed(String breed, Pageable pageable) {
        return null;
    }


}
