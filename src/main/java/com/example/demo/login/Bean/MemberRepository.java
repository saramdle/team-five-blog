package com.example.demo.login.Bean;

import com.example.demo.login.Dto.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberModel, Long> {

    MemberModel findByUserEmail(String userEmail);

    MemberModel findByUserNm(String userNm);

    MemberModel deleteByUid(int uid);

}
