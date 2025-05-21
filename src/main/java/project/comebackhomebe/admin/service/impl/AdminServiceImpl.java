package project.comebackhomebe.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.comebackhomebe.admin.service.AdminService;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogDiscoverInfoResponse;
import project.comebackhomebe.domain.dog.dogInfo.entity.Dog;
import project.comebackhomebe.domain.dog.dogInfo.repository.DogRepositoryCustom;
import project.comebackhomebe.domain.member.dto.MemberInfo;
import project.comebackhomebe.domain.member.entity.Member;
import project.comebackhomebe.domain.member.repository.MemberRepositoryCustom;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final MemberRepositoryCustom memberRepository;
    private final DogRepositoryCustom dogRepository;

    @Override
    public List<MemberInfo> getMemberInfo(Pageable pageable) {
        Page<Member> memberPage = memberRepository.getAllMembers(pageable);

        List<Member> members = memberPage.getContent();

        return MemberInfo.listOf(members);
    }

    @Override
    public List<DogDiscoverInfoResponse> getDogInfo(Pageable pageable) {
        Page<Dog> dogPage = dogRepository.getDogInfo(pageable);

        List<Dog> dogs = dogPage.getContent();

        return DogDiscoverInfoResponse.listOf(dogs);
    }
}
