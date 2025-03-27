package project.comebackhomebe.domain.dog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.comebackhomebe.domain.dog.repository.DogRepository;
import project.comebackhomebe.domain.dog.service.DogService;

@RequiredArgsConstructor
@Service
@Transactional
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
}
