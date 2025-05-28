package project.comebackhomebe.domain.member.entity;

public enum Role {
    /**
     * USER : 일반 사용자
     * ADMIN : 모니터링 권한 X
     * SUPERADMIN : 모니터링 권한 O
     */
    USER, ADMIN, SUPERADMIN
}
