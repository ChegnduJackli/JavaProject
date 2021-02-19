package com.example.springbootdemo.entity;


import org.springframework.stereotype.Component;

//@Data
//@AllArgsConstructor//有参构造
//@NoArgsConstructor//无参构造
@Component//注册bean到容器中
public class Dog {
    private String name;
    private Integer age;
}
