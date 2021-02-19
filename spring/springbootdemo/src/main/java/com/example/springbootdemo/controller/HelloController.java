package com.example.springbootdemo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//  @ResponseBody and controller合并版本
@RestController
public class HelloController {

    @Value("${name}")
    private String name;

    @Value("${person.age}")
    private String age;

    @RequestMapping("/hello")
    public String hello() {
        return "hello,this is a springboot demo";
    }
    @RequestMapping("/quick")
    public String quick(){
        return "hello39 sprintboot";
    }

    //获得配置yml信息
    @RequestMapping("/getyml")
    public String GetYml(){

        return "name:"+ name + " age: "+ age;
    }

}
