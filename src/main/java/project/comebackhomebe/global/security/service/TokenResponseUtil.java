package project.comebackhomebe.global.security.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenResponseUtil {

    /**
     * key = 키 값 , value = jwt값
     * Cookie 로 저장하기 위해 생성
     *
     * @param key
     * @param value
     * @return
     */
    public ResponseCookie createCookie(String key, String value) {

        ResponseCookie cookie = ResponseCookie.from(key, value)
                .httpOnly(true)
                .secure(true) // 로컬 개발 시엔 false
                .path("/")
                .maxAge(86400)
                .sameSite("None") // :white_check_mark: 여기가 핵심!
                .build();

        return cookie;
    }

    public String getCookie(HttpServletRequest request) {
        String refresh = null; // 여기서 원래 refresh 토큰 받아야함?

        // 리프레쉬 토큰 추출
        Cookie[] cookies = request.getCookies();
        // 리프레쉬 토큰 추출
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return "오류" + HttpStatus.BAD_REQUEST;
        }

        return refresh;
    }

    public void expiredCookie(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("refresh", null);
        expiredCookie.setPath("/");
        expiredCookie.setHttpOnly(true);
        expiredCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(expiredCookie);
    }

}
