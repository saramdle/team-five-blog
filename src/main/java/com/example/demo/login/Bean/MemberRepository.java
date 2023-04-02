package com.example.demo.login.Bean;

import com.example.demo.login.Dto.MemberDto;
import com.example.demo.login.Dto.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberModel, Long> {

    MemberDto findByUserEmail(String userEmail);

    MemberDto findByUserNm(String userNm);

    MemberDto deleteByUid(int uid);

}
