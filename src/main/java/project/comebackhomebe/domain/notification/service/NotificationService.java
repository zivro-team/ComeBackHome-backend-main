package project.comebackhomebe.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;

import java.util.List;

public interface NotificationService {

    // 공통 로직 : 알림 보내기
    void sendMessage(String token, String title, String message) throws FirebaseMessagingException;

    // 새로운 강아지 등록 알림
    void registerNewDogFromBreed(String breed) throws FirebaseMessagingException;

    // 근처에서 강아지 발견 알림
    void findDogByBoundary(String area) throws FirebaseMessagingException;

    // 매칭 가능성 알림
    void matchDogByAi(List<Dog> dogs) throws FirebaseMessagingException;
}
