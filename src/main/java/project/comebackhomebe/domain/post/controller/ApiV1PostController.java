package project.comebackhomebe.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.comebackhomebe.domain.post.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class ApiV1PostController {
    private final PostService postService;
}
