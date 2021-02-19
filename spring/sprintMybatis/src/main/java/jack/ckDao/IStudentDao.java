package jack.ckDao;

import jack.ckEntity.Student;

public interface IStudentDao {
    public Student findStudentById(int id);
}
