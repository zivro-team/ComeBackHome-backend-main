package project.comebackhomebe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(servers = {@Server(url = "https://cbh.kro.kr", description = "Default Server URL")})
@SpringBootApplication
@EnableJpaAuditing
public class ComebackhomeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComebackhomeBeApplication.class, args);
    }

}
