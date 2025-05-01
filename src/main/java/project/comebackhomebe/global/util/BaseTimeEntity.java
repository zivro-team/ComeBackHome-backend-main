package project.comebackhomebe.global.util;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 이 클래스를 상속할 경우 필드들도 컬럼으로 인식
@NoArgsConstructor(access = PROTECTED) // 기본 생성자 생성, protected 접근 제어
@AllArgsConstructor(access = PROTECTED) // 모든 필드 값을 파라미터로 받는 생성자 생성
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 사용
@ToString // toString 메서드 자동 생성
@EqualsAndHashCode // equals와 hashCode 메서드 자동 생성
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;
}
