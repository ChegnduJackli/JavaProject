import com.cjx.dao.IUserDao;
import com.cjx.entity.SelectUserParam;
import com.cjx.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class test {
    public static void main(String[] args) throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产一个SqlSession对象
        SqlSession  session= factory.openSession();
        //4.使用SqlSession创建dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();

        for (User user :
                users) {
            System.out.println(user);
        }

        User u1 = userDao.selectById(1L);
        System.out.println(u1);

        User selectUser =new User();
        selectUser.setUsername("god");
        //selectUser.setSex("F");
        List<User> u2 = userDao.selectByIf(selectUser);
        System.out.println(u2.stream().count());

        //where id in ('1','2')
//        List<Integer> listId=new ArrayList<Integer>();
//        listId.add(1);
//        listId.add(100);
//        List<User> u2 = userDao.selectByForEach(listId);
//        for (User user : u2) {
//            System.out.println(user);
//        }

        //queyr by pojo
        SelectUserParam su =new  SelectUserParam();
        su.setUsername("god");
        List<User> u3 = userDao.selectByPojo(su);

        for (User user : u3) {
            System.out.println(user);
        }


        Random ran1 = new Random(1000);
        User addU =new User();
       // addU.setId(100);
        addU.setUsername("god"+ran1.nextInt());
        addU.setAddress("chengdu");
        addU.setBirthday(new Date());
        addU.setSex("M");
       int  addResult = userDao.addUser(addU);
        System.out.println("添加了" + addResult + "条记录");
//        session.insert("com.cjx.dao.IUserDao.addUser",addU);
//        session.commit();

//        User updateU =new User();
//        //updateU.setId(2);
//        updateU.setUsername("jack");
//        updateU.setSex("M");
//        int updateResult = userDao.updateUser(updateU);
//        System.out.println("update" + updateResult + "条记录");
//
//        int deleteResult = userDao.deleteUser(2L);
//        System.out.println("delete" + deleteResult + "条记录");

        session.commit();

        //5.使用代理对象执行方法
          users = userDao.findAll();

        for (User user :  users) {
            System.out.println(user);
        }


//        RoleMapper roleDao = session.getMapper(RoleMapper.class);
//        role r = roleDao.getRole(1L);
//        System.out.println(r);
//
//        RoleMapper2 roleDao2 = session.getMapper(RoleMapper2.class);
//        role r2 = roleDao2.getRole(1L);
//        System.out.println(r2);

        //6.释放资源

        session.close();
        in.close();
    }
}
