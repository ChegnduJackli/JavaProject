//package com.annotation;
//
////创建 Service 层接口的实现类
//
//import ckDao.PersonANDao;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
////通常作用在业务层（Service 层），用于将业务层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。
////上述代码中，首先使用 @Service 注解将 PersonServiceImpl 类标识为 Spring 中的 Bean，其写法相当于配置文件中
//// <bean id="personANService"class="com.mengma.annotation.PersonServiceImpl"/> 的书写。
//@Service("personANService")
//public class PersonANServiceImpl implements PersonANService {
//
//    //这相当于配置文件中 <property name="personDao"ref="personDao"/> 的写法。
//    @Resource(name = "personANDao")
//    private PersonANDao personANDao;
//    public PersonANDao getPersonANDao() {
//        return personANDao;
//    }
//    @Override
//    public void add() {
//        personANDao.add();// 调用personDao中的add()方法
//        System.out.println("Service层的add()方法执行了...");
//    }
//}
