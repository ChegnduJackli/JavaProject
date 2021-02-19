package ckController;

import ckService.PersonANService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//其写法相当于在配置文件中编写 <bean id="personAction"class="com.mengma.annotation.PersonAction"/>。

@Controller("personANAction")
public class PersonANAction {
    @Resource(name = "personANService")
    private PersonANService personService;
    public PersonANService getPersonService() {
        return personService;
    }
    public void add() {
        personService.add(); // 调用personService中的add()方法
        System.out.println("Action层的add()方法执行了...");
    }
}