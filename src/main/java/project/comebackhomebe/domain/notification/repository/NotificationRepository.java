package project.comebackhomebe.domain.notification.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import project.comebackhomebe.domain.notification.entity.Notification;

public interface NotificationRepository extends ElasticsearchRepository<Notification, String> {
    // 이거 ELK 적용해야할듯
}
