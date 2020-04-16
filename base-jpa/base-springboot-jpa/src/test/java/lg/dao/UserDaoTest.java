package lg.dao;

import lg.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-10-08 18:42
 * desc:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUpdate(){
        System.out.println(userDao.updateSomeThing("2",1));
    }


    @Test
    public void findAll(){
        List<User> all = userDao.findAll();
        System.out.println(all.size());
    }

    @Test
    public void jpaSpacFind(){
        boolean flag = true;
        Specification<User> rule = new Specification<User>() {
            private static final long serialVersionUID = -6693737962123400130L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> age = root.get("age");
                Predicate equal = null;
                if(flag)
                    equal = cb.equal(age, 12);
                return equal;
            }
        };
        Optional<User> one = userDao.findOne(rule);
        System.out.println(one);

    }



}