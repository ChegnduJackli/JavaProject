package com.mengma.apectj_xml;

import dao.CustomerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.testng.annotations.Test;

public class XMLTest {
    @Test
    public void test() {
        String xmlPath = "src/main/java/com/mengma/apectj_xml/applicationContext.xml";

        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
                xmlPath);

        // 从spring容器获取实例
        CustomerDao customerDao = (CustomerDao) applicationContext
                .getBean("customerDao");
        // 执行方法
        customerDao.add();
        customerDao.update();
    }
}