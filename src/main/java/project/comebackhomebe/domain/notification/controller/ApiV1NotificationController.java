package project.comebackhomebe.domain.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.dto.response.NotificationResponse;
import project.comebackhomebe.domain.notification.service.NotificationService;
import project.comebackhomebe.global.firebase.dto.FCMTokenRequest;
import project.comebackhomebe.global.firebase.service.FCMService;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "알림 서비스 라우터", description = "알림 보내는 API 입니다. ")
public class ApiV1NotificationController {
    private final NotificationService notificationService;
    private final FCMService fcmService;

    // FCM 토큰 저장 서비스 API
    @PostMapping("/token/{userId}")
    public ResponseEntity<String> getToken(@PathVariable Long userId, @Valid @RequestBody FCMTokenRequest tokenRequest) {
        return ResponseEntity.ok(fcmService.getToken(userId, tokenRequest.token()));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> sendMessage(@RequestBody NotificationRequest request) throws FirebaseMessagingException {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }
}
