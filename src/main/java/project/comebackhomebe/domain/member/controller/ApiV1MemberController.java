package project.comebackhomebe.domain.member.controller;

import co.elastic.clients.elasticsearch._types.Refresh;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.domain.member.service.MemberService;
import project.comebackhomebe.global.config.security.handler.RefreshHandler;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final MemberService memberService;
    private final RefreshHandler refreshHandler;

    @GetMapping("/main")
    public String main(){
        return "성공";
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        refreshHandler.refreshHandler(request, response);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
