package com.example.springbootdemo.controller;

import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//自动从容器中找到数据
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getUserList")
    public String quick4(){
        return String.valueOf(userMapper.findAll());
    }
//    @RequestMapping("/selectById/:id")
    @RequestMapping(value="/selectById/{id}", method = RequestMethod.GET)
    public String selectById(@PathVariable Long id){
        return String.valueOf(userMapper.selectById(id));
    }

    //如果RequestParam 有括号参数，定义变量可以随便，
    //如果没有括号参数，变量必须要跟前台传递的一致
    @RequestMapping(value="/selectById2", method = RequestMethod.GET)
    public String selectById2(@RequestParam("id") Long id2){
        return String.valueOf(userMapper.selectById(id2));
    }

    @GetMapping(value="/selectById3")
    public String selectById3(@RequestParam Long id2){
        return String.valueOf(userMapper.selectById(id2));
    }
    @PostMapping("/addUser")
    public String AddUser(@RequestBody User user){
        Integer result = userMapper.addUser(user);
        return  "add user count:"+ result.toString();
    }

    @DeleteMapping("/deleteUser/{id}")
    public String DeleteUser(@PathVariable("id") Long id){
        Integer result = userMapper.deleteUser(id);
        return  "delete user count:"+ result.toString();
    }

    @PutMapping("/updateUser/{id}")
    public String DeleteUser(@PathVariable("id") int id,@RequestBody User user){
        user.setId(id);
        Integer result = userMapper.updateUser(user);
        return  "update user count:"+ result.toString();
    }


}