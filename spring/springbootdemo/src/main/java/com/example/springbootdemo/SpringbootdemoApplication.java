package com.example.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//    通过使用@MapperScan可以指定要扫描的Mapper类的包的路径，比如：
@SpringBootApplication
//@MapperScan("com.example.springbootdemo.dao.*.Mapper")
public class SpringbootdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootdemoApplication.class, args);
    }

}
