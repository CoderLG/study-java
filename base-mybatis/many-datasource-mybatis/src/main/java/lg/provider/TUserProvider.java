package lg.provider;

import lg.domain.TUser;
import org.apache.ibatis.jdbc.SQL;

/**
 * author: LG
 * date: 2019-09-07 12:19
 * desc:
 */
public class TUserProvider {

    public String updateVideo(final TUser tUser) {
        return new SQL(){
            {
                UPDATE("t_user");

                if (tUser.getTName() != null) {
                    SET("t_name=#{tName}");

                }

                //*******  有多少字段可以写多少字段
                WHERE("t_id=#{tId}");
            }
        }.toString();
    }
}

