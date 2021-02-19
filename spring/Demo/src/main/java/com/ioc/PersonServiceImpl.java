package com.ioc;

import dao.PersonDao;

// 通过属性 setter 注入的案例演示 Spring 容器是如何实现依赖注入
public class PersonServiceImpl implements PersonService{

    // 定义接口声明
    private PersonDao personDao;
    // 提供set()方法，用于依赖注入
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }


    @Override
    public void addPerson() {
        personDao.add(); // 调用PersonDao中的add()方法
        System.out.println("addPerson()执行了...");
    }
}
