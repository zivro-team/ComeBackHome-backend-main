package project.comebackhomebe.domain.dog.dogInfo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;

public interface DogRepositoryCustom {
    // 전체 게시글 가져오기
    Slice<Dog> getAllDogInfo(Pageable pageable);

    // 카테고리 (타입)
    Slice<Dog> getDogInfoByType(Type type, Pageable pageable);

    // 카테고리 (종)
    Slice<Dog> getDogInfoByBreed(String breed, Pageable pageable);

    // 관리자
    Page<Dog> getDogInfo(Pageable pageable);
}
