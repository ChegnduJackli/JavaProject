package jack.ckControler;

import jack.ckDao.IStudentDao;
import jack.ckEntity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("studentController")
public class StudentController {
    @Autowired
    private IStudentDao studentDao;

    public void test() {
        Student s = studentDao.findStudentById(1);
        System.out.println("run StudentController");
        System.out.println("学生姓名：" + s.getUsername());
        System.out.println("学生密码：" + s.getPassword());
        System.out.println("学生邮箱：" + s.getEmail());

    }
}
