package com.mengma.factorybean;

import dao.CustomerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.testng.annotations.Test;

public class FactoryBeanTest {
    @Test
    public void test() {
        String xmlPath = "src/main/java/com/mengma/factorybean/applicationContext.xml";
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
                xmlPath);
        CustomerDao customerDao = (CustomerDao) applicationContext
                .getBean("customerDaoProxy");
        customerDao.add();
        customerDao.update();
        customerDao.delete();
        customerDao.find();
    }
}
