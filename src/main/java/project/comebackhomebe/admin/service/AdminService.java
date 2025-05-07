package project.comebackhomebe.admin.service;

import org.springframework.data.domain.Pageable;
import project.comebackhomebe.domain.dog.dogInfo.dto.response.DogInfoResponse;
import project.comebackhomebe.domain.member.dto.MemberInfo;

import java.util.List;

public interface AdminService {
    // 회원정보 가져오기
    List<MemberInfo> getMemberInfo(Pageable pageable);

    // 강아지정보 가져오기
    List<DogInfoResponse> getDogInfo(Pageable pageable);
}
