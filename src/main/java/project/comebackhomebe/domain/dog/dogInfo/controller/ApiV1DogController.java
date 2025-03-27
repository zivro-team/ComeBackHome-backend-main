package project.comebackhomebe.domain.dog.dogInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.domain.dog.dogInfo.service.DogService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dogInfo")
public class ApiV1DogController {

    private final DogService dogService;

}
