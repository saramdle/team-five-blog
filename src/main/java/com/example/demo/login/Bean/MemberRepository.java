package com.example.demo.login.Bean;

import com.example.demo.login.Dto.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberModel, Long> {

    MemberModel findByUid(int uid);

    MemberModel findByUserEmail(String userEmail);

    MemberModel findByUserNm(String userNm);

    MemberModel deleteByUid(int uid);

}
