package project.comebackhomebe.domain.dog.dogInfo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogDiscoverAllRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.DogLostAllRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Type;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/dogInfo")
@Tag(name = "강아지 정보 라우터", description = "강아지 정보를 처리합니다.")
public class ApiV2DogController {

    private final DogService dogService;

    // 정보 생성
    @PostMapping("/lost")
    @Operation(summary = "강아지 정보 생성", description = "강아지 신고 LOST API 입니다.")
    public ResponseEntity<DogInfoResponse> createLostDogInfo(@RequestBody DogLostAllRequest dogAllRequest,
                                                             HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(dogService.createLostInfo(
                dogAllRequest.dogLostInfoRequest(),
                dogAllRequest.dogImageRequest(),
                dogAllRequest.dogHealthRequest(),
                request
        ));
    }

    @PostMapping("/discover")
    @Operation(summary = "강아지 정보 생성", description = "강아지 신고 LOST API 입니다.")
    public ResponseEntity<DogInfoResponse> createDiscoverDogInfo(@RequestBody DogDiscoverAllRequest dogAllRequest,
                                                                 HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(dogService.createDiscoverInfo(
                dogAllRequest.dogDiscoverInfoRequest(),
                dogAllRequest.dogImageRequest(),
                dogAllRequest.dogHealthRequest(),
                request
        ));
    }


    //
    // 정보 가져오기
    @GetMapping("/{id}")
    @Operation(summary = "특정 강아지 정보 가져오기", description = "특정 강아지 정보를 가져올때 사용하는 API")
    public ResponseEntity<DogInfoResponse> getDogInfo(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(dogService.getInfo(id));
    }

    // 정보 전체 가져오기
    @GetMapping
    @Operation(summary = "강아지 전체 정보 가져오기", description = "강아지 정보 전체를 받아오는 API")
    public ResponseEntity<List<DogInfoResponse>> getAllDogInfo(Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getList(pageable));
    }

    // 정보 수정하기
    @PatchMapping("/{id}")
    @Operation(summary = "강아지 찾음으로 수정할때 사용", description = "강아지 수정으로 바꿈")
    public ResponseEntity<DogInfoResponse> updateDogInfo(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(dogService.foundInfo(id));
    }


    // 신고, 피신고 분류
    @GetMapping("/type/{type}")
    @Operation(summary = "신고, 피신고 구분 강아지 정보 가져오기", description = "잃어버린 강아지와 발견한 강아지 따로 분류")
    public ResponseEntity<List<DogInfoResponse>> getDogInfoByType(@PathVariable("type") Type type,
                                                                  Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getListByType(type, pageable));
    }

    // 종 분류
    @GetMapping("/breed/{breed}")
    @Operation(summary = "강아지 종을 기준으로 분류", description = "강아지 종 별로 따로 보이게 분류")
    public ResponseEntity<List<DogInfoResponse>> getDogInfoByType(@PathVariable("breed") String breed,
                                                                  Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getListByBreed(breed, pageable));
    }

    // 정보 삭제하기
    @DeleteMapping("/{id}")
    @Operation(summary = "강아지 정보 삭제", description = "강아지 정보를 삭제")
    public void deleteDogInfo(@PathVariable("id") Long id) throws IOException {
        dogService.deleteInfo(id);
    }
}

