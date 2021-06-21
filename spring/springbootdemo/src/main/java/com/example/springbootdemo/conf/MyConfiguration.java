package com.example.springbootdemo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
    @Bean
    public String helloBean(){
        return "welcom to bean";
    }
}
