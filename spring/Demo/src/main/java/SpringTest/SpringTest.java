package SpringTest;


import ckController.PersonANAction;
import ckService.AccountService;
import com.instance.factory.Person3;
import com.instance.static_factory.Person2;
import com.ioc.PersonService;
import dao.PersonDao;
import dao.TestDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

//创建demo步骤
//https://blog.csdn.net/jiahanghacker/article/details/88871207
public class SpringTest {
    @Test
    public void demo() {
        //初始化Spring容器ApplicationContext，加载配置文件
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        //通过容器获取testDao实例
        TestDao testDao = (TestDao) application.getBean("testDao");
        testDao.sayHello();

        // 通过容器获取personDao实例
//        PersonDao personDao = (PersonDao) application.getBean("personDao");
//        // 调用 personDao 的 add ()方法
//        personDao.add();
//
//        // 通过容器获取personService实例
        PersonService personService = (PersonService) application.getBean("personService");
        // 调用personService的addPerson()方法
        personService.addPerson();

        // 通过容器获取id为person2实例
//        Person2 p2 = (Person2) application.getBean("person2");
//        p2.run();
//两次输出的结果相同，这说明 Spring 容器只创建了一个 Person 类的实例。
// 由于 Spring 容器默认作用域是 singleton，如果不设置 scope="singleton"，则其输出结果也将是一个实例。
//        System.out.println(application.getBean("personDao"));
//        System.out.println(application.getBean("personDao"));

//        Person3 p3 = (Person3) application.getBean("person3");
//        p3.run();

        // 设值方式输出结果
//        System.out.println(application.getBean("personBean1"));
//        // 构造方式输出结果
//        System.out.println(application.getBean("personBean2"));

        // 获得personAction实例
        PersonANAction personAction = (PersonANAction) application
                .getBean("personANAction");
        // 调用personAction中的add()方法
        personAction.add();

//        AccountService accountService = (AccountService) application
//                .getBean("accountService");
//        accountService.transfer("zhangsan", "lisi", 100);

    }
}
