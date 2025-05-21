package project.comebackhomebe.global.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
class DatastoreConfiguration {
    @Configuration
    @EnableElasticsearchRepositories(basePackages = "project.comebackhomebe.domain.notification.repository")
    static class ElasticsearchConfig {}

    @Configuration
    @EnableMongoRepositories(basePackages = {
            "project.comebackhomebe.domain.chat.chatMessage.repository",
            "project.comebackhomebe.domain.chat.chatRoom.repository"
    })
    static class MongoConfig {}
    @Configuration
    @EnableRedisRepositories(basePackages = "project.comebackhomebe.global.redis.repository")
    static class RedisConfig {}

    @Configuration
    @EnableJpaRepositories(basePackages = "project.comebackhomebe.domain",
            excludeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = {
                            project.comebackhomebe.domain.notification.repository.NotificationRepository.class,
                            project.comebackhomebe.domain.chat.chatMessage.repository.ChatMessageRepository.class,
                            project.comebackhomebe.domain.chat.chatRoom.repository.ChatRoomRepository.class
                    }
            )
    )
    static class JpaConfig {}
}
