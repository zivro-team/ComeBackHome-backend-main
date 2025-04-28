package project.comebackhomebe.domain.dog.dogInfo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.comebackhomebe.domain.dog.dogHealth.dto.request.DogHealthRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.DogImageRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.request.ImageRequest;
import project.comebackhomebe.domain.dog.dogImage.dto.response.ImageResponse;
import project.comebackhomebe.domain.dog.dogImage.service.ImageService;
import project.comebackhomebe.domain.dog.dogInfo.dto.request.InfoRequest;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.InfoResponse;
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
    @PostMapping("/{type}")
    @Operation(summary = "강아지 정보 생성", description = "강아지 신고 API 입니다.")
    @Parameters({
            @Parameter(name = "breed", description = "강아지 종", example = "골든 리트리버"),
            @Parameter(name = "height", description = "강아지 크기", example = "24cm"),
            @Parameter(name = "gender", description = "강아지 성별", example = "MALE"),
            @Parameter(name = "health_status_1", description = "영양상태", example = "제처중, 갈비뼈가 너무 튀어나옴"),
            @Parameter(name = "health_status_2", description = "피부상태", example = "부분적인 탈모"),
            @Parameter(name = "health_status_3", description = "외형상태", example = "꼬리 색이 파란색"),
            @Parameter(name = "feature", description = "특징", example = "개 목걸이가 걸려있음"),

    })
    public ResponseEntity<InfoResponse> createDogInfo(@PathVariable("type") Type type,
                                                      @RequestPart("infoRequest") @Valid InfoRequest infoRequest,
                                                      @RequestPart("healthRequest") @Valid DogHealthRequest healthRequest,
                                                      @RequestPart("images") @Valid List<DogImageRequest> imageRequest) throws IOException {
        return ResponseEntity.ok(dogService.createInfos(type, infoRequest.breed(), infoRequest.gender(), infoRequest.height(), imageRequest, healthRequest));
    }

    //
    // 정보 가져오기
    @GetMapping("/{id}")
    @Operation(summary = "특정 강아지 정보 가져오기", description = "특정 강아지 정보를 가져올때 사용하는 API")
    public ResponseEntity<InfoResponse> getDogInfo(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(dogService.getInfo(id));
    }

    // 정보 전체 가져오기
    @GetMapping
    @Operation(summary = "강아지 전체 정보 가져오기", description = "강아지 정보 전체를 받아오는 API")
    public ResponseEntity<List<InfoResponse>> getAllDogInfo(Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getList(pageable));
    }

    // 정보 수정하기
    @PatchMapping("/{id}")
    @Operation(summary = "강아지 찾음으로 수정할때 사용", description = "강아지 수정으로 바꿈")
    public ResponseEntity<InfoResponse> updateDogInfo(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(dogService.updateInfo(id));
    }


    // 신고, 피신고 분류
    @GetMapping("/type/{type}")
    @Operation(summary = "신고, 피신고 구분 강아지 정보 가져오기", description = "잃어버린 강아지와 발견한 강아지 따로 분류")
    public ResponseEntity<List<InfoResponse>> getDogInfoByType(@PathVariable("type") Type type,
                                                         Pageable pageable) throws IOException {
        return ResponseEntity.ok(dogService.getListByType(type, pageable));
    }

    // 종 분류
    @GetMapping("/breed/{breed}")
    @Operation(summary = "강아지 종을 기준으로 분류", description = "강아지 종 별로 따로 보이게 분류")
    public ResponseEntity<List<InfoResponse>> getDogInfoByType(@PathVariable("breed") String breed,
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

