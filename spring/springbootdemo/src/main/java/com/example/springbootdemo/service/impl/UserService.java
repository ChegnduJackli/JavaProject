package com.example.springbootdemo.service.impl;


import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int updateUser(User u) {
        int result=  this.jdbcTemplate.update("update User set sex =?"
                + " where username =?", "G", "god");
        return result;
    }

    @Override
    public int batchSaveUserSql(){
        String sql = "insert into user(id,username,birthday,sex,address) values(?,?,?,?,?)" ;
        List<Object[]> paramList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String[] arr = new String[5];
            arr[0] = (10 +i)+"";
            arr[1] = "jack"+i;
            arr[2] = "2020-01-01";
            arr[3] = "M";
            arr[4] = "chengdu";
            paramList.add(arr);
        }
        int [] result = jdbcTemplate.batchUpdate(sql,paramList);
        return result.length;
   }
}
