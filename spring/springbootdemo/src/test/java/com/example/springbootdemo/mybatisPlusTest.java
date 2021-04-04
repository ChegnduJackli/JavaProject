package com.example.springbootdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.IPlusUserService;
import com.example.springbootdemo.service.impl.plusUserService;
import com.sun.el.parser.BooleanNode;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

//mybatis plus 测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class mybatisPlusTest {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private IPlusUserService _plusUserService;

    @Test
    public void test() {
        List<User> data = userMapper.selectList(null);
        List<User> data2 = userMapper.findAll();
        System.out.println(data);
        System.out.println(data2);
    }
    @Test
    public void TestQueryPage(){
        //总页数+总记录数
        Page<User> page = new Page<>(2, 5);
        //调用自定义sql
        IPage<User> iPage = userMapper.selectPage(page, null);
        long total =iPage.getTotal();
    }

    @Test
    public void TestQuery(){

//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//
//        String name ="jack1";
//        String sex="M";
//
//        if(StringUtils.isNotEmpty(name)){
//            queryWrapper.like(User::getUserName,name);
//        }
//        if(StringUtils.isNotEmpty(sex)){
//            queryWrapper.eq(User::getSex,sex);
//        }
//        List<User> users = userMapper.selectList(queryWrapper);
//User user =  userMapper.selectById(3);

        HashMap<String,Object> map= new HashMap<>();
        map.put("username","jack19");
        map.put("sex","M");
        final List<User> users = userMapper.selectByMap(map);

    }
    @Test
    public void TestDelete(){
        int id =4791;
      int count = userMapper.deleteById(id);
        //int count = userMapper.deleteBatchIds(Arrays.asList(1, 2, 4, 5));
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
//        wrapper.eq(User::getUserName,"jack19")
//                .eq(User::getSex,"M");
//        userMapper.delete(wrapper);





    }
    //test insert 批量和单个
    @Test
    public void TestInsert(){

//        List<User> paramList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            User arr = new User();
//            arr.setUserName("jack" + i);
//            arr.setSex("M");
//            arr.setAddress("chengdu");
//            arr.setBirthday(new Date());
//            paramList.add(arr);
//        }
//        Boolean b = _plusUserService.saveBatch(paramList);

        User user = new User();
        //user.setId(18);
        user.setUserName("jackd");
        userMapper.insert(user);


    }

    //test update

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void TestUpdate(){

        //lambda更新实践
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(User::getId, 6);
//        User user2 = new User();
//        //user2.setId(2);
//        user2.setUserName("jack2005");
//        userMapper.update(user2,updateWrapper);
        updateWrapper.eq(User::getId,6).set(User::getUserName,"jack99");
        userMapper.update(null, updateWrapper);
      //int a  = 1/0;

        User user3 = new User();
        user3.setId(7);
        user3.setUserName("jack update02");
        userMapper.updateById(user3);


    }
}
