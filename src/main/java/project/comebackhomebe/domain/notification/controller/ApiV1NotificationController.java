package project.comebackhomebe.domain.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.notification.dto.request.NotificationRequest;
import project.comebackhomebe.domain.notification.service.NotificationService;
import project.comebackhomebe.global.CustomRateLimiter.RateLimited;
import project.comebackhomebe.global.firebase.dto.FCMTokenRequest;
import project.comebackhomebe.global.firebase.service.FCMService;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "알림 서비스 라우터", description = "알림 관련 API 입니다. ")
public class ApiV1NotificationController {
    private final NotificationService notificationService;
    private final FCMService fcmService;

    // FCM 토큰 저장 서비스 API
    @RateLimited
    @PostMapping("/token/{userId}")
    @Operation(summary = "토큰 받아오기", description = "FCM 토큰을 서버한테 넘겨줍니다.")
    @Parameters({
            @Parameter(name = "token", description = "FCM 토큰", example = "123asd")
    })
    public ResponseEntity<String> getToken(@PathVariable Long userId, @Valid @RequestBody FCMTokenRequest tokenRequest) {
        return ResponseEntity.ok(fcmService.getToken(userId, tokenRequest.token()));
    }

    @RateLimited
    @PostMapping
    @Operation(summary = "알림 보내기", description = "해당 사용자에게 알림을 보냅니다.")
    @Parameters({
            @Parameter(name = "userId", description = "사용자 고유 id", example = "1"),
            @Parameter(name = "token", description = "FCM 토큰", example = "123asd"),
            @Parameter(name = "title", description = "제목", example = "골든 리트리버 발견"),
            @Parameter(name = "message", description = "메시지", example = "해당 품종에 강아지가 발견되었습니다!")
    })
    public void sendMessage(@RequestBody NotificationRequest request) throws FirebaseMessagingException {
        notificationService.sendMessage(request.token(), request.title(), request.message());
    }
}
