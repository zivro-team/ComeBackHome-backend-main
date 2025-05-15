package project.comebackhomebe.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepository;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.member.repository.MemberRepository;
import project.comebackhomebe.domain.member.repository.MemberRepositoryCustom;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.dto.response.NotificationResponse;
import project.comebackhomebe.domain.notification.entity.Notification;
import project.comebackhomebe.domain.notification.repository.NotificationRepository;
import project.comebackhomebe.domain.notification.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final MemberRepositoryCustom memberRepositoryCustom;
    private final MemberRepository memberRepository;

    @Override
    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        // 성공 로그 남기기
        log.info("FCM 메시지 전송 성공, response: {}", response);
    }

    @Override
    public void registerNewDogFromBreed(String breed) throws FirebaseMessagingException {
        List<String> tokens = memberRepositoryCustom.getFcmTokensByBreed(breed);

        for (String token : tokens){
            sendMessage(
                    token,
                    breed + "종의 강아지가 발견되었습니다!",
                    "빠르게 확인해보세요");
        }
    }

    @Override
    public void findDogByBoundary(String area) throws FirebaseMessagingException {
        List<String> tokens = memberRepositoryCustom.getFcmTokensByArea(area);

        for (String token : tokens){
            sendMessage(
                    token,
                    area + "에서 강아지가 발견되었습니다!",
                    "빠르게 확인해보세요");
        }

    }

    @Override
    public void matchDogByAi (List<Dog> dogs) throws FirebaseMessagingException {
        List<String> tokens = new ArrayList<>();

        for (Dog dog : dogs){
            String token = memberRepository.findFcmTokenByDogId(dog.getId());
            tokens.add(token);
        }

        for (String token : tokens){
            sendMessage(
                    token,
                     "AI 분석 결과 사용자의 강아지랑 유사합니다!",
                    "빠르게 확인해보세요");
        }


    }


}
