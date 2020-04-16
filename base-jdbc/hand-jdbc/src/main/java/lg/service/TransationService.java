package lg.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lg.common.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

/**
 * author: LG
 * date: 2019-09-04 22:12
 * desc:
 */
@Slf4j
@Service
public class TransationService {
    @Autowired
    private DruidDataSource dataSource;

    public <User> User pgQuery(String sql, Function<ResultSet, User> func) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
//		Connection con = getConnection();
        Statement st = null;
        ResultSet rs = null;
        User t = null;
        try {
            if (null != con) {
                st = con.createStatement();
                if (null != st) {
                    rs = st.executeQuery(sql);
                    if (null != rs && null != func) {
                        t = func.apply(rs);
                        rs.close();
                    }
                    st.close();
                }
                con.close();
            }
        } catch (Exception ex) {

            log.error(ex.getMessage(), ex);
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != st)
                    st.close();
                if (null != con)
                    con.close();
            } catch (SQLException ex) {

                log.error( ex.getMessage(), ex);
            }
        }
        return t;
    }
}
