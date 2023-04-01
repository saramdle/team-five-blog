package com.example.demo.login.Bean;

import com.example.demo.login.Dto.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberModel, Long> {

    Optional<MemberModel> findByUid(int uid);

    Optional<MemberModel> findByUserEmail(String userEmail);

    Optional<MemberModel> deleteByUid(int uid);

}
