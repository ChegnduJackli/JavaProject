package com.example.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
//@Component
public class User {

    @TableId(type= IdType.AUTO)
    private int id;
    private String userName;
    private Date birthday;
    private String sex;
    private String address;

    @TableLogic
    private Integer deleted;
    //自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
