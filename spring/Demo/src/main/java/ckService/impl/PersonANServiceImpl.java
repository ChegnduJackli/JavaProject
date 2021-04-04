package ckService.impl;

import ckService.PersonANService;
import ckDao.PersonANDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("personANService")
public class PersonANServiceImpl implements PersonANService {

    //这相当于配置文件中 <property name="personDao"ref="personDao"/> 的写法。
    @Resource(name = "personANDao")
    private PersonANDao personANDao;

    public PersonANDao getPersonANDao() {
        return personANDao;
    }

    @Override
    public void add() {
        personANDao.add();// 调用personDao中的add()方法
        System.out.println("Service层的add()方法执行了...");
    }
}
