package com.cjx.dao;

import com.cjx.entity.SelectUserParam;
import com.cjx.entity.User;

import java.util.List;

public interface IUserDao {
     List<User> findAll();

     User selectById(Long id);

     List<User> selectByIf(User u);

     List<User> selectByForEach(List<Integer> idList);

     List<User> selectByPojo(SelectUserParam u);


     int addUser(User u);

     int updateUser(User u );

     int deleteUser(long id);


}
