package project.comebackhomebe.domain.dog.dogInfo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.InfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dogInfo")
public class ApiV1DogController {

    private final DogService dogService;

    // 정보 생성
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<InfoResponse> createDogInfo(@RequestPart("infoRequest") @Valid InfoRequest infoRequest,
                                                  @RequestPart("images") List<MultipartFile> images) throws IOException {
        return ResponseEntity.ok(dogService.createInfo(infoRequest.breed(), infoRequest.gender(), infoRequest.height(), images));
    }
    //
    // 정보 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<InfoResponse> getDogInfo(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(dogService.getInfo(id));
    }

    // 정보 전체 가져오기
    @GetMapping
    public ResponseEntity<List<InfoResponse>> getAllDogInfo(Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getList());
    }

    // 정보 수정하기

    // 정보 삭제하기

}
