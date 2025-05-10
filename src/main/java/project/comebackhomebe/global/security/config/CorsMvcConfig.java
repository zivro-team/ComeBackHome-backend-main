package project.comebackhomebe.global.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins("http://localhost:3000", "http://cbh.kro.kr", "https://cbh.kro.kr")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 필요한 메서드 추가
                .exposedHeaders("Authorization")  // 👈 이거 추가
                .allowCredentials(true); // 쿠키 등 자격 정보 허용 시
    }
}
