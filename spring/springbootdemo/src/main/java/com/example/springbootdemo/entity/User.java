package com.example.springbootdemo.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
//@Component
public class User {
    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}
