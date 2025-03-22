package project.comebackhomebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComebackhomeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComebackhomeBeApplication.class, args);
    }

}
