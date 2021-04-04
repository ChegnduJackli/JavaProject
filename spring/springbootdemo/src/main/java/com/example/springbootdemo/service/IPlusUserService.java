package com.example.springbootdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdemo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IPlusUserService extends IService<User> {
}
