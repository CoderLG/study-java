package lg.dao;

import lg.domain.TUser;
import lg.provider.TUserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-06 14:16
 * desc:
 */
public interface TUserDao {

    @Insert("insert into t_user (t_name) values(#{tName})")
    @Options(useGeneratedKeys = true,keyProperty = "tId",keyColumn = "t_id")    //获得自增id
    int save(TUser tUser);


    @Select("select * from t_user")
    List<TUser> findAll();

    @Select("select * from t_user where t_id = #{tId}")
    TUser findById(Long tId);

    //@Update("update t_user set t_name = #{tName} where t_id = #{tId}")
    @UpdateProvider(type = TUserProvider.class,method = "updateVideo")
    int update (TUser tuer);

    @Delete("delete from t_user where t_id = #{tId}")
    int delete(Long tId);
}
