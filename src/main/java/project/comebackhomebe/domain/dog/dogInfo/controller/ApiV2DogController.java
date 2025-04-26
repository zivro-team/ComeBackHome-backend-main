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
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/dogInfo")
@Tag(name = "강아지 정보 라우터", description = "강아지 정보를 처리합니다.")
public class ApiV2DogController {

    private final DogService dogService;
    private final ImageService imageService;

    // 정보 생성
    @PostMapping
    @Operation(summary = "강아지 정보 생성 (LOST)", description = "강아지를 잃어버렸을 때 사용하는 API")
    @Parameters({
            @Parameter(name = "breed", description = "강아지 종", example = "골든 리트리버"),
            @Parameter(name = "height", description = "강아지 크기", example = "24cm"),
            @Parameter(name = "gender", description = "강아지 성별", example = "MALE"),
            @Parameter(name = "health_status_1", description = "영양상태", example = "제처중, 갈비뼈가 너무 튀어나옴"),
            @Parameter(name = "health_status_2", description = "피부상태", example = "부분적인 탈모"),
            @Parameter(name = "health_status_3", description = "외형상태", example = "꼬리 색이 파란색"),
            @Parameter(name = "feature", description = "특징", example = "개 목걸이가 걸려있음"),

    })
    public ResponseEntity<InfoResponse> createDogInfo(@RequestPart("infoRequest") @Valid InfoRequest infoRequest,
                                                      @RequestPart("healthRequest") @Valid DogHealthRequest healthRequest,
                                                      @RequestPart("images") @Valid List<DogImageRequest> imageRequest) throws IOException {
        return ResponseEntity.ok(dogService.getInfo(infoRequest.breed(), infoRequest.gender(), infoRequest.height(), imageRequest, healthRequest));
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
    @PostMapping("/test")
    public ResponseEntity<List<String>> testImageUrls(@RequestPart("images") List<MultipartFile> images) throws IOException {
        return ResponseEntity.ok(imageService.saveImage(images));
    }
    // 정보 삭제하기

}

