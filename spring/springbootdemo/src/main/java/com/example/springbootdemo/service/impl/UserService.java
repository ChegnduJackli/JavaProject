package com.example.springbootdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.IPlusUserService;
import com.example.springbootdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private IPlusUserService _plusUserService;

    @Override
    public int updateUser(User u) {
        int result = this.jdbcTemplate.update("update User set sex =?"
                + " where username =?", "G", "god");
        return result;
    }

    @Override
    public int batchSaveUserSql() {
        String sql = "insert into user(id,username,birthday,sex,address) values(?,?,?,?,?)";
        List<Object[]> paramList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String[] arr = new String[5];
            arr[0] = (10 + i) + "";
            arr[1] = "jack" + i;
            arr[2] = "2020-01-01";
            arr[3] = "M";
            arr[4] = "chengdu";
            paramList.add(arr);
        }
        int[] result = jdbcTemplate.batchUpdate(sql, paramList);
        return result.length;
    }

    public int TestPlus(){

//        List<User> paramList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            User arr = new User();
//            arr.setUserName("jack" + i);
//            arr.setSex("M");
//            arr.setAddress("chengdu");
//            arr.setBirthday(new Date());
//            paramList.add(arr);
//            userMapper.insert(arr);
//        }
        //批量插入就单个循环插入
        //Boolean b = _plusUserService.saveBatch(paramList);

        //测试插入
        User user3 = new User();
        user3.setUserName("大神2");
        user3.setSex("M");
        user3.setAddress("xxx");
        userMapper.insert(user3);

        User user = new User();
        user.setId(2);
        user.setUserName("jack111");
        userMapper.updateById(user);

        //lambda更新实践
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, 2);
        User user2 = new User();
        user2.setId(2);
        user2.setUserName("jack228");
        userMapper.update(user2,updateWrapper);


return 1;
    }
}
