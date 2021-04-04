package com.example.springbootdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> findAll();

    User selectById(Long id);

    List<User> selectByIf(User u);

    List<User> selectByForEach(List<Integer> idList);

//    List<User> selectByPojo(SelectUserParam u);


    int addUser(User u);

    int updateUser(User u);

    int deleteUser(long id);
}
