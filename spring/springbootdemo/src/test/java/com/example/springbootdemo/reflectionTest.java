package com.example.springbootdemo;

import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.Message;
import com.example.springbootdemo.service.impl.Student;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class reflectionTest {
    //反射
    @Autowired
    private UserMapper userMapper;
    //三种方式常用第三种，第一种对象都有了还要反射干什么。第二种需要导入类的包，依赖太强，不导包就抛编译错误。
    // 一般都第三种，一个字符串可以传入也可写在配置文件中等多种方法。
    @Test
    public void test() throws  Exception {
        //方式1
//        Student stu1 =new Student();
//        Class stuClass = stu1.getClass();//获取Class对象
//        System.out.println(stuClass.getName());
//
//        //方式2
//        //第二种方式获取Class对象
//        Class stuClass2 = Student.class;
//        System.out.println(stuClass == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个

        //第三种方式获取Class对象
        //try {
            Class clazz = Class.forName("com.example.springbootdemo.service.impl.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            //System.out.println(clazz == stuClass2);//判断三种方式是否获取的是同一个Class对象
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


//        //2.获取所有公有构造方法
//        System.out.println("**********************所有公有构造方法*********************************");
//        Constructor[] conArray = clazz.getConstructors();
//        for(Constructor c : conArray){
//            System.out.println(c);
//        }
//
//
//        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
//        conArray = clazz.getDeclaredConstructors();
//        for(Constructor c : conArray){
//            System.out.println(c);
//        }
//
//        System.out.println("*****************获取公有、无参的构造方法*******************************");
//        Constructor con = clazz.getConstructor(null);
//        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
//        //2>、返回的是描述这个无参构造函数的类对象。
//
//        System.out.println("con = " + con);
//        //调用构造方法
//        Object obj = con.newInstance();
//        //	System.out.println("obj = " + obj);
//        //	Student stu = (Student)obj;
//
//        System.out.println("******************获取私有构造方法，并调用*******************************");
//        con = clazz.getDeclaredConstructor(char.class);
//        System.out.println(con);
//        //调用构造方法
//        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
//        obj = con.newInstance('男');
//
//2.获取所有公有方法
        Class stuClass = Class.forName("com.example.springbootdemo.service.impl.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
        System.out.println("***************获取所有的”公有“方法*******************");
        stuClass.getMethods();
        Method[] methodArray = stuClass.getMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        System.out.println("***************获取所有的方法，包括私有的*******************");
        methodArray = stuClass.getDeclaredMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        System.out.println("***************获取公有的show1()方法*******************");
        Method m = stuClass.getMethod("show1", String.class);
        System.out.println(m);
        //实例化一个Student对象
        Object obj = stuClass.getConstructor().newInstance();
        m.invoke(obj, "刘德华");

        System.out.println("***************获取私有的show4()方法******************");
        m = stuClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);//解除私有限定
        Object result = m.invoke(obj, 20);//需要两个参数，一个是要调用的对象（获取有反射），一个是实参
        System.out.println("返回值：" + result);


    }



}
