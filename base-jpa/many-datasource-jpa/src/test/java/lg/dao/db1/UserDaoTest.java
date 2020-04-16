package lg.dao.db1;

import lg.domain.db1.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-10-30 18:58
 * desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private  UserDao userDao;


    @Test
    public void testSave() throws Exception {
        List<User> all = userDao.findAll();
        System.out.println(all.size());

    }
}