package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;

public interface IUserService {
    int updateUser(User u );
    int batchSaveUserSql();
}
