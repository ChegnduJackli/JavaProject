//package com.annotation;
//
//import ckDao.PersonANDao;
//import org.springframework.stereotype.Repository;
//
////其写法相当于配置文件中 <bean id="personANDao"class="com.annotation.PersonANDaoImpl"/> 的书写
////用于将数据访问层（DAO层）的类标识为 Spring 中的 Bean，其功能与 @Component 相同。
//@Repository("personANDao")
//public class PersonANDaoImpl implements PersonANDao {
//    @Override
//    public void add() {
//        System.out.println("Dao层的add()方法执行了...");
//    }
//}
