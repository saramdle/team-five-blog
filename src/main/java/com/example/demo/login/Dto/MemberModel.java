package com.example.demo.login.Dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public abstract class MemberModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 300, nullable = false)
    private String userName;

    @Column(length = 300, nullable = false)
    private String userNickNm;

    @Column(length = 300, nullable = false)
    private String userSignDate;

    @Column(length = 100, nullable = false, unique = true)
    private String userEmail;

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
