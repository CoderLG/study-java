package lg.dao;

import lg.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

    @Query(value = "select * from t_user where name like ?1" ,nativeQuery = true)
    List<Object  []> querySomeThing(String name);

    @Modifying
    @Query(value = "update t_user set name = :na where user_id = :id" ,nativeQuery = true)
    Integer updateSomeThing(@Param("na")String name, @Param("id") Integer user_id);


    @Modifying
    @Query(value = "update t_user set name = 0 where age = :age" ,nativeQuery = true)
    Integer updateAllByUserAge(@Param("age") Integer user_age);

    @Modifying
    @Query(value = "update t_user set name = 1 where user_id = :id" ,nativeQuery = true)
    Integer updateByUserId( @Param("id") Integer user_id);



    //OrderBy must not be used more than once in a method name!
    Page<User> findByNameLikeAndGridSetLikeOrderByUserIdDesc(String name, String set, Pageable pageable);






}
