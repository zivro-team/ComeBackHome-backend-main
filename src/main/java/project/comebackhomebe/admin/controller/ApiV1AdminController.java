package project.comebackhomebe.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.admin.service.AdminService;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogDiscoverInfoResponse;
import project.comebackhomebe.domain.member.dto.MemberInfo;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class ApiV1AdminController {
    private final AdminService adminService;

    // 회원정보
    @GetMapping("/members")
    public ResponseEntity<List<MemberInfo>> getMembers(@Valid Pageable pageable) {
        return ResponseEntity.ok(adminService.getMemberInfo(pageable));
    }

    // 견종
    @GetMapping("/dogs")
    public ResponseEntity<List<DogDiscoverInfoResponse>> getDogs(@Valid Pageable pageable) {
        return ResponseEntity.ok(adminService.getDogInfo(pageable));
    }

}
