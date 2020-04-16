package lg.service;

import lg.domain.TUser;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-07 09:59
 * desc:
 */
public interface UserService {

    int save(TUser tUser);

    List<TUser> findAll();


    TUser findById(Long id);


    int update (TUser tuer);

    int delete(Long id);

    int deleteAll();
}
