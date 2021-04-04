package jack.ckControler;


import java.util.List;

import jack.ckDao.IUserDao;
import jack.ckEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("userController")
public class UserController {
    @Autowired
    private IUserDao userDao;

    public void test() {
        // 查询一个用户
        User auser = userDao.selectById(1L);
        System.out.println(auser);
        System.out.println("============================");

        // 添加一个用户
        User addmu = new User();
        addmu.setUsername("陈恒");
        addmu.setSex("男");
        int add = userDao.addUser(addmu);
        System.out.println("添加了" + add + "条记录");
        System.out.println("============================");
        // 修改一个用户
        User updatemu = new User();
        updatemu.setId(1);
        updatemu.setUsername("张三");
        updatemu.setSex("M");
        int up = userDao.updateUser(updatemu);
        System.out.println("修改了" + up + "条记录");
        System.out.println("============================");
        // 删除一个用户
        int dl = userDao.deleteUser(9);
        System.out.println("删除了" + dl + "条记录");
        System.out.println("============================");
        // 查询所有用户
        List<User> list = userDao.findAll();
        for (User myUser : list) {
            System.out.println(myUser);
        }
    }
}