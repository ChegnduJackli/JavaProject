//package com.example.springbootdemo;
//
//import com.example.springbootdemo.dao.UserMapper;
//import com.example.springbootdemo.entity.User;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes=SpringbootdemoApplication.class)
//public class RedisTest {
//
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void  test(){
//        //1,从redis获取数据，json字符串
//        String userListJson = redisTemplate.boundValueOps("user.findAll").get();
//
//        //2,判断redis是否存在数据
//        if(userListJson ==null){
//            //3,不存在数据，从数据库中查询
//            List<User> all = userMapper.findAll();
//            //4，将数据存在redis缓存中
//            //向将list集合转换为json格式,使用jackson进行转换
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                userListJson=  objectMapper.writeValueAsString(all);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            redisTemplate.boundValueOps("user.findAll").set(userListJson);
//            System.out.println("----从数据库中获取User数据---");
//        }
//        else{
//            System.out.println("----从Redis缓存中获取User数据---");
//        }
//        System.out.println(userListJson);
//
//        //4,将数据在控制台打印
//    }
//}
