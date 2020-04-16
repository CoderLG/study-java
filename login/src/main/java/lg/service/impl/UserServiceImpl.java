package lg.service.impl;

import lg.dao.TUserDao;
import lg.domain.TUser;
import lg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-07 10:00
 * desc:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserDao userDao;

    @Override
    public int save(TUser tUser) {
        return userDao.save(tUser);
    }

    @Override
    public List<TUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public TUser findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public int update(TUser tuer) {
        return  userDao.update(tuer);
    }

    @Override
    public int delete(Long id) {
        return userDao.delete(id);
    }
}
