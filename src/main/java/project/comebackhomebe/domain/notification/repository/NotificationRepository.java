package project.comebackhomebe.domain.notification.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import project.comebackhomebe.domain.notification.entity.Notification;

public interface NotificationRepository extends ElasticsearchRepository<Notification, String> {
}
