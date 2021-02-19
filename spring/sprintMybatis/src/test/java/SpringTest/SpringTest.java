package SpringTest;



import jack.ckControler.StudentController;
import jack.ckControler.UserController;
import org.testng.annotations.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//创建demo步骤
//https://blog.csdn.net/jiahanghacker/article/details/88871207
public class SpringTest {
    @Test
    public void demo() {
        //初始化Spring容器ApplicationContext，加载配置文件
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
//        StudentMapper sd = (StudentMapper) application.getBean("studentMapper");
//        Student s = sd.findStudentById(1);
//        System.out.println("学生姓名：" + s.getUsername());
//        System.out.println("学生密码：" + s.getPassword());
//        System.out.println("学生邮箱：" + s.getEmail());

        UserController uc = (UserController) application
                .getBean("userController");
        uc.test();

//        IStudentDao sd = (IStudentDao) application.getBean("studentMapper");
//        Student s = sd.findStudentById(1);
//        System.out.println("学生姓名：" + s.getUsername());
//        System.out.println("学生密码：" + s.getPassword());
//        System.out.println("学生邮箱：" + s.getEmail());

        StudentController st = (StudentController) application
                .getBean("studentController");
        st.test();

    }
}
