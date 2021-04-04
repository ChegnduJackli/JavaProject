package com.example.springbootdemo;

import com.example.springbootdemo.common.ImapHelper;
import com.example.springbootdemo.dao.UserMapper;
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

        String str = "20201001";   //
        Date format1 = null;
        try {
            format1 = new SimpleDateFormat("yyyyMMdd").parse(str);

            String longDate = new SimpleDateFormat("yyyy-MM-dd").format(format1);
            format1 = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (Exception e) {

        }

        //_mailMapper.ReadMail();
        Log("test");
    }

    private void Log(String msg) {
        System.out.println(msg);

    }
}
