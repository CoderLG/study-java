package lg.provider;

import lg.dvo.DMSJReq;
import org.apache.ibatis.jdbc.SQL;

/**
 * author: LG
 * date: 2019-09-07 12:19
 * desc:
 */
public class PlaceProvider {

    public String updateVideo(final DMSJReq entity) {
        return new SQL(){
            {
                SELECT("*");
                FROM("GFGX_Y_DMK_DMSJ");

            }
        }.toString();
    }
}

