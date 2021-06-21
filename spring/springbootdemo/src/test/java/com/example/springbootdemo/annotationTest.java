package com.example.springbootdemo;

import com.example.springbootdemo.common.ImapHelper;
import com.example.springbootdemo.entity.Dog;
import com.example.springbootdemo.entity.Dto.OrgDto;
import com.example.springbootdemo.entity.Org;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.InitSex;
import com.example.springbootdemo.service.OrgField;
import com.example.springbootdemo.service.ValidateAge;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class annotationTest {

    @Autowired
    private ImapHelper _mailMapper;

     void printResult(boolean checkResult) {
        if (checkResult) {
            System.out.println("校验通过");
        } else {
            System.out.println("校验未通过");
        }
    }

    @Test
    public void test() throws IllegalAccessException {
        Dog dog = new Dog();
        initUser(dog);
        // 年龄为0，校验为通过情况
        boolean checkResult = checkUser(dog);
        printResult(checkResult);
        // 重新设置年龄，校验通过情况
        dog.setAge(22);
        checkResult = checkUser(dog);
        printResult(checkResult);

        System.out.println(dog);
    }


    @Test
    public void test2() throws IllegalAccessException {

        List<OrgDto> orgDtos = new ArrayList<>();
        var orgDto1 = new OrgDto();
        orgDto1.setId(1);
        orgDto1.setValue("name");
        orgDtos.add(orgDto1);

        var orgDto2 = new OrgDto();
        orgDto2.setId(2);
        orgDto2.setValue("taxNo");
        orgDtos.add(orgDto2);

        var orgDto3 = new OrgDto();
        orgDto3.setId(3);
        orgDto3.setValue("buyerName");
        orgDtos.add(orgDto3);

        var org = new Org();
        InitOrg(orgDtos,org);
        System.out.println(org);

    }

    private void Log(String msg) {
        System.out.println(msg);

    }

    private  void InitOrg(List<OrgDto> dtoList ,Org org) throws IllegalAccessException {
        Field[] fields = Org.class.getDeclaredFields();
            // 遍历所有属性
            for (Field field : fields) {
                // 如果属性上有此注解，则进行赋值操作
                if (field.isAnnotationPresent(OrgField.class)) {
                    OrgField init = field.getAnnotation(OrgField.class);
                    for (OrgDto dto : dtoList) {
                    if(init.id() == dto.getId()) {
                        field.setAccessible(true);
                        // 设置属性的性别值
                        field.set(org, dto.getValue());
                    }
                    //System.out.println("完成属性值的修改，修改值为:" + init.sex().toString());
                }
            }
        }

    }

    private void initUser(Dog dog) throws IllegalAccessException {
        // 获取User类中所有的属性(getFields无法获得private属性)
        Field[] fields = Dog.class.getDeclaredFields();
        // 遍历所有属性
        for (Field field : fields) {
            // 如果属性上有此注解，则进行赋值操作
            if (field.isAnnotationPresent(InitSex.class)) {
                InitSex init = field.getAnnotation(InitSex.class);
                field.setAccessible(true);
                // 设置属性的性别值
                field.set(dog, init.sex().toString());
                System.out.println("完成属性值的修改，修改值为:" + init.sex().toString());
            }
        }
    }

     boolean checkUser(Dog dog) throws IllegalAccessException {
        // 获取User类中所有的属性(getFields无法获得private属性)
        Field[] fields = Dog.class.getDeclaredFields();
        boolean result = true;
        // 遍历所有属性
        for (Field field : fields) {
            // 如果属性上有此注解，则进行赋值操作
            if (field.isAnnotationPresent(ValidateAge.class)) {
                ValidateAge validateAge = field.getAnnotation(ValidateAge.class);
                field.setAccessible(true);
                int age = (int) field.get(dog);
                if (age < validateAge.min() || age > validateAge.max()) {
                    result = false;
                    System.out.println("年龄值不符合条件");
                }
            }
        }
        return result;
    }
}
