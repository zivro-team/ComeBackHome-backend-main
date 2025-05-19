package project.comebackhomebe.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Role;

public interface JwtService {
    String resolveAccessToken(HttpServletRequest request);

    String resolveRefreshToken(HttpServletRequest request);

    String generateAccessToken(String category, String verifyKey, String username, String email, Role role, Long expiredMs);

    String generateRefreshToken(Long expiredMs);

    String generateNewAccessToken(MemberInfo memberInfo, String type);

    String generateNewRefreshToken();
}
