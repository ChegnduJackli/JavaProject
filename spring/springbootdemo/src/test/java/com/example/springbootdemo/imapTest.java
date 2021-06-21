package com.example.springbootdemo;

import com.example.springbootdemo.common.ImapHelper;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.EmailInfoDto;
import com.example.springbootdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class imapTest {

    @Autowired
    private ImapHelper _mailMapper;

    @Test
    public void test() {
        List<EmailInfoDto>  emaiList =   _mailMapper.ReadMail();
        Log("test");
    }

    @Test
    public void test2() {
      String s =" 临沂丽珍贸易有限公司 - 电子发票 - 202104 温度计订单";
        int index = s.indexOf("电子发票");
        int keyLen ="电子发票".length();
        String str1 = s.substring(index+keyLen,s.length()).trim();
        String str2 =str1.replaceFirst("-","");
        System.out.println(str2);
    }


    private void Log(String msg) {
        System.out.println(msg);

    }
}
