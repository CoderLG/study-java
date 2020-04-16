package lg.service;

import lg.dao.db1.UserDao;
import lg.dao.db2.StudentDao;
import lg.domain.db1.User;
import lg.domain.db2.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * author: LG
 * date: 2019-10-31 08:49
 * desc:
 */
@Service
public class ServiceImpl {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    // @Transactional 分布式事务由第三方整个 多个数据源，当遇到问题后 都回滚，资源耗费多，慢
    public void saveDoubleFaile(){
        User user = new User();
        user.setName("1");
        user.setUserId(1);

        Student student = new Student();
        student.setId(2);
        student.setName("2");
        userDao.save(user);
        studentDao.save(student);

        int  a= 1/0;
    }



}
