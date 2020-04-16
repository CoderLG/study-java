package geovis.services.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PGHelper {

	static boolean first = true;

	public <T> T pgQuery(String sql, Function<ResultSet, T> func) {
		HikariDataSource dataSource = DataSource.getInstance();
		Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        T t = null;

		try {
            con = dataSource.getConnection();
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
//			Logger lgr = Logger.getLogger(PGHelper.class);
//			lgr.log(Level.ERROR, ex.getMessage(), ex);
            log.error("执行sql出错",ex);
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != st)
					st.close();
				if (null != con)
					con.close();
			} catch (SQLException ex) {
                log.error("关闭数据库连接出错",ex);
			}
		}
		return t;
	}

	public String pgRun(String sql) {
		HikariDataSource dataSource = DataSource.getInstance();
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			Logger lgr = Logger.getLogger(PGHelper.class);
			lgr.log(Level.ERROR, e.getMessage(), e);
		}
		Statement st = null;
		String strResult = "OK";
		try {
			String insertSql1 = sql.replace("'null'", "''");
			if (null != con) {
				st = con.createStatement();
				if (null != st) {
					st.execute(insertSql1);
					st.close();
				}
				con.close();
			}
		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(PGHelper.class);
			lgr.log(Level.ERROR, ex.getMessage(), ex);
			strResult = ex.getMessage();
		} finally {
			try {
				if (null != st)
					st.close();
				if (null != con)
					con.close();
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(PGHelper.class);
				lgr.log(Level.ERROR, ex.getMessage(), ex);
				strResult = ex.getMessage();
			}
		}
		return strResult;

	}

	public <T> T getBySql(String sql, Function<ResultSet, T> action) {
		if (action == null)
			return null;

		T result = pgQuery(sql, rs -> {
			try {
				while (rs.next()) {
					T t1 = action.apply(rs);
					return t1;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		});
		return result;
	}

	public <T> List<T> getListBySql(String sql, Function<ResultSet, T> action) {
		if (action == null)
			return null;

		List<T> result = pgQuery(sql, rs -> {
			try {
				ArrayList<T> list = new ArrayList<T>();
				while (rs.next()) {
					T t1 = action.apply(rs);
					list.add(t1);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
		return result;
	}

	public static String createInsertSql(String TableName, List<Object> values) {

		Stream<String> query = values.stream().map(v -> {
			String str = String.format("'%s'", v);
			if (v == null)
				return "null";
			else
				return str;
		});

		String con = query.collect(Collectors.joining(","));
		String insertSql = String.format("INSERT INTO \"%s\" VALUES (%s)", TableName, con);
		return insertSql;
	}

	public static String createUpdateSql(String TableName, Map<String, Object> values, String KeyName,
			String KeyValue) {

		List<String> list = new ArrayList<String>();

		values.forEach((k, v) -> {

			if (v != null && v.toString().startsWith("ST_"))
				list.add(String.format(" \"%s\" = %s ", k, v));
			else
				list.add(String.format(" \"%s\" = '%s' ", k, v));

		});

		String con = list.stream().collect(Collectors.joining(","));
		String insertSql = String.format("UPDATE \"%s\" SET  %s  WHERE \"%s\" = '%s' ", TableName, con, KeyName,
				KeyValue);
		return insertSql;
	}

	public boolean exist(String sql) {
		HikariDataSource dataSource = DataSource.getInstance();
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			Logger lgr = Logger.getLogger(PGHelper.class);
			lgr.log(Level.ERROR, e.getMessage(), e);
		}
		Statement st = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			if (null != con) {
				st = con.createStatement();
				if (null != st) {
					rs = st.executeQuery(sql);
					if (null != rs) {
						while (rs.next()) {
							flag = true;
						}
					}
					st.close();
				}
				con.close();
			}
		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(PGHelper.class);
			lgr.log(Level.ERROR, ex.getMessage(), ex);

		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != st)
					st.close();
				if (null != con)
					con.close();
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(PGHelper.class);
				lgr.log(Level.ERROR, ex.getMessage(), ex);
			}
		}
		return flag;
	}

}
