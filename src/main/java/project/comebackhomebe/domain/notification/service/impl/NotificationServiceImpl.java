package project.comebackhomebe.domain.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.member.repository.MemberRepositoryCustom;
import project.comebackhomebe.domain.notification.entity.Notification;
import project.comebackhomebe.domain.notification.repository.NotificationRepository;
import project.comebackhomebe.domain.notification.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepositoryCustom memberRepositoryCustom;

    /**
     * 새롭게 등록된 강아지의 종을 추출하여
     * 이미 등록되어 있는 DB에 접근하여
     * 해당 강아지의 종을 등록한 사용자에게 알림을 보냄
     *
     * @param breed : 강아지 종
     * @throws FirebaseMessagingException
     */
    @Override
    public void registerNewDogFromBreed(String breed) throws FirebaseMessagingException {
        List<String> tokens = memberRepositoryCustom.getFcmTokensByBreed(breed);

        for (String token : tokens) {
            sendMessage(
                    token,
                    breed + "종의 강아지가 발견되었습니다!",
                    "빠르게 확인해보세요");
        }
    }

    /**
     * 새롭게 등록된 강아지의 지역을 추출하여
     * 이미 등록되어 있는 DB에 접근하여
     * 해당 강아지의 지역을 등록한 사용자에게 알림을 보냄
     *
     * @param area : 지역
     * @throws FirebaseMessagingException
     */
    @Override
    public void findDogByBoundary(String area) throws FirebaseMessagingException {
        List<String> tokens = memberRepositoryCustom.getFcmTokensByArea(area);

        for (String token : tokens) {
            sendMessage(
                    token,
                    area + "에서 강아지가 발견되었습니다!",
                    "빠르게 확인해보세요");
        }

    }

    /**
     * AI 유사도 분석을 통해
     * 유사도가 비슷한 강아지 3마리를 찾고
     * 해당 신고를 한 사용자에게 알림을 보냄
     *
     * @param dogs : 유사도 비슷 강아지들
     * @throws FirebaseMessagingException
     */
    @Override
    public void matchDogByAi(List<Dog> dogs) throws FirebaseMessagingException {

        List<Long> dogIds = dogs.stream()
                .map(Dog::getId)
                .collect(Collectors.toList());

        List<String> tokens = memberRepositoryCustom.getFcmTokensByDog(dogIds);

        for (String token : tokens) {
            sendMessage(
                    token,
                    "AI 분석 결과 사용자의 강아지와 유사한 강아지가 있습니다!",
                    "빠르게 확인해보세요");
        }
    }

    /**
     * 알림을 보내는 로직
     * FCM에 있는 메시지로 빌더 만들고
     * Firebase로 메시지 보내기
     * 알림 엔티티에 저장하기
     *
     * @param token : fcmToken
     * @param title : 알림 제목
     * @param body  : 알림 내용
     * @throws FirebaseMessagingException
     */
    @Override
    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        FirebaseMessaging.getInstance().send(message);

        Notification notification = Notification.from(token, title, body);

        notificationRepository.save(notification);
    }
}
