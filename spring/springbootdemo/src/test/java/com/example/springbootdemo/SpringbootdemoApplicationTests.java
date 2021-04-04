package com.example.springbootdemo;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
@MapperScan("com.example.springbootdemo.dao")
@SpringBootTest
class SpringbootdemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
