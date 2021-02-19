package jack.ckDao;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import jack.ckEntity.User;
import java.util.List;

@Repository("userDao")
@Mapper
public interface IUserDao {
     List<User> findAll();

     User selectById(Long id);

//     List<User> selectByIf(User u);
//
//     List<User> selectByForEach(List<Integer> idList);
//
//     //List<User> selectByPojo(SelectUserParam u);
//
//
     int addUser(User u);

     int updateUser(User u );

     int deleteUser(long id);


}
