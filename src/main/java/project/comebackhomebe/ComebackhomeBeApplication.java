package project.comebackhomebe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import project.comebackhomebe.domain.chat.chatMessage.repository.ChatMessageRepository;
import project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository;
import project.comebackhomebe.domain.notification.repository.NotificationRepository;

@OpenAPIDefinition(servers = {@Server(url = "https://cbh.kro.kr", description = "Default Server URL")})
@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class ComebackhomeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComebackhomeBeApplication.class, args);
    }

}
