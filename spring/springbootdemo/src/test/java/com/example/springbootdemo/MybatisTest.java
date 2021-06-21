package com.example.springbootdemo;

import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.entity.User;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        List<User> data = userMapper.findAll();
        System.out.println(data);

    }
    @Test
    public void test2() throws ParseException {
        int minusMonth =6;
        String period="20210101";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Date datePeriod = format.parse(period);

        Calendar c = Calendar.getInstance();
        c.setTime(datePeriod);
        c.add(Calendar.MONTH, -minusMonth);
        Date m6 = c.getTime();
        String startDate = format.format(m6);

    }
    @Test
    public void test3() throws ParseException {
        int minusMonth =6;
        String period="202101";
       String tt1 =  getLastPeriod(period);

    }
    public String getLastPeriod(String period){
        try {
            String strPeriod = period + "01";
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date datePeriod = format.parse(strPeriod);
            Calendar c = Calendar.getInstance();
            c.setTime(datePeriod);
            c.add(Calendar.MONTH, -1);
            Date lastMonth = c.getTime();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

            String lastPeriod = sdf.format(lastMonth);
            return lastPeriod;
        }
        catch (Exception ex){

        }
        return "";
    }

}
