package com.example.springbootdemo.controller;

import com.example.springbootdemo.common.RedisUtil;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//自动从容器中找到数据
@RestController
@RequestMapping("/api/person")
public class PersonController {



    //    @Autowired
    @Resource
    private Person person;

    @RequestMapping("/quick3")
    public String quick3() {

        return String.valueOf(person);
    }

}
