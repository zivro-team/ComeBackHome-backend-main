package project.comebackhomebe.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.global.CustomRateLimiter.RateLimited;
import project.comebackhomebe.global.firebase.dto.FCMTokenRequest;
import project.comebackhomebe.global.firebase.service.FCMService;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "알림 서비스 라우터", description = "알림 관련 API 입니다. ")
public class ApiV1NotificationController {
    private final FCMService fcmService;

    /**
     * 사용자의 FCM 토큰을 가져오기
     *
     * @param tokenRequest : 토큰
     * @param request      : 액세스토큰
     * @return
     */
    @PostMapping("/token")
    @Operation(summary = "토큰 받아오기", description = "FCM 토큰을 서버한테 넘겨줍니다.")
    @Parameters({
            @Parameter(name = "token", description = "FCM 토큰", example = "123asd")
    })
    public ResponseEntity<String> getToken(@Valid @RequestBody FCMTokenRequest tokenRequest,
                                           HttpServletRequest request) {
        return ResponseEntity.ok(fcmService.getToken(request, tokenRequest.token()));
    }
}
