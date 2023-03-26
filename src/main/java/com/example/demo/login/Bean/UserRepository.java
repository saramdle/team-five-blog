package com.example.demo.login.Bean;

import com.example.demo.login.Dto.UserModel;

public interface UserRepository {

    public UserModel findByUid(int uid);
    public UserModel save(UserModel model);
}
