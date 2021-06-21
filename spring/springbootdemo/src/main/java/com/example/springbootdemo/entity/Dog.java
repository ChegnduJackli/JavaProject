package com.example.springbootdemo.entity;


import com.example.springbootdemo.service.InitSex;
import com.example.springbootdemo.service.ValidateAge;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
//@AllArgsConstructor//有参构造
//@NoArgsConstructor//无参构造
@Component//注册bean到容器中
public class Dog {
    private String name;
    @ValidateAge(min = 20, max = 35, value = 22)
    private int age;

    @InitSex(sex = InitSex.SEX_TYPE.MAN)
    private String sex;

}
