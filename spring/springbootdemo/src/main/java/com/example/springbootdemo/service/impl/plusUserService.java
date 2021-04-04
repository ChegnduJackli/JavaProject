package com.example.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.IPlusUserService;
import org.springframework.stereotype.Service;

@Service
public class plusUserService extends ServiceImpl<UserMapper, User> implements IPlusUserService {
}
