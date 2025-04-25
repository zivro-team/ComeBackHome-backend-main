package project.comebackhomebe.global.security.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

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

    public Object getPrincipal(HttpServletRequest request) {
        return request.getUserPrincipal();
    }

    public void expiredCookie(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("refresh", null);
        expiredCookie.setPath("/");
        expiredCookie.setHttpOnly(true);
        expiredCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(expiredCookie);
    }

}
