package com.example.springbootdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//  @ResponseBody and controller合并版本
@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @Value("${person.name}")
    private String name;

    @Value("${person.age}")
    private String age;

    @Value("${person.address}")
    private String address;

    @Autowired
    String helloBean;

    @RequestMapping("/hello")
    public String hello() {
        return "hello,this is a springboot demo";
    }

    @RequestMapping("/quick")
    public String quick() {
        return "hello39 sprintboot";
    }

    @GetMapping("/getHelloBean")
    public String test() {

        System.out.println("main函数开始执行");
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("===task start===");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("===task finish===");
            }
        });

        thread.start();
        System.out.println("main函数执行结束");

        return helloBean;
    }


    //获得配置yml信息
    @RequestMapping("/getyml")
    public String GetYml() {
        return "name:" + name + " age: " + age + " address:" + address + ": from yml";
    }

}
