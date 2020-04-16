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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DBHelper {

    @Autowired
    DruidDataSource dataSource;
//	@Value("${Host}")
//	private String url;
//	@Value("${Username}")
//	private String user;
//	@Value("${Password}")
//	private String password;
//	@Value("${Database}")
//	private String database;
//
//	static HikariDataSource hds;

//	public Connection getConnection() {
//		try {
//			String namespace = "Data.Postgres.Placename.V1";
////			String host = DependHelper.get(namespace, "Host");
////			String user = DependHelper.get(namespace, "Username");
////			String password = DependHelper.get(namespace, "Password");
////			String database = DependHelper.get(namespace, "Database");
////			String port = DependHelper.get(namespace, "Port");
////
////			if (port == null || port.length() == 0)
////				port = "5432";
////
////			String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
////			if (first) {
////				System.out.println(url);
////				first = false;
////			}
//			String str=String.format("jdbc:postgresql://%s:5432/%s", url,database);
//			System.out.println(str);
//
//			if (hds == null) {
//				Log.info("进入获取");
//				hds = new HikariDataSource();
//				hds.setDriverClassName("org.postgresql.Driver");
//				hds.setJdbcUrl(str);
//				hds.setUsername(user);
//				hds.setPassword(password);
//				hds.setMaximumPoolSize(50);
//				hds.setMaxLifetime(5000);
//				hds.setIdleTimeout(4000);
//			}
//			return hds.getConnection();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			hds = null;
//		}
//
//		return null;
//
//	}

//	public static Connection getConnection(String url, String user, String password) {
//		try {
//			return DriverManager.getConnection(url, user, password);
//		} catch (SQLException e) {
//			System.out.println("connection failed");
//			return null;
//		}
//	}

	public <T> T pgQuery(String sql, Function<ResultSet, T> func) {

		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
//		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		T t = null;
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

//	public static <T> List<T> pgQuery(String sql, geovis.services.common.Consumer<ResultSet, T> func,
//			Supplier<T> getObj) {
//		Connection con = getConnection();
//		Statement st = null;
//		ResultSet rs = null;
//		List<T> list = null;
//		try {
//			if (null != con) {
//				st = con.createStatement();
//				if (null != st) {
//					rs = st.executeQuery(sql);
//					if (null != rs) {
//						list = new ArrayList<T>();
//						while (rs.next()) {
//							T t = getObj.get();
//							func.accept(rs, t);
//							list.add(t);
//						}
//					}
//					st.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			Logger lgr = Logger.getLogger(PGHelper.class);
//			lgr.log(Level.ERROR, ex.getMessage(), ex);
//		} finally {
//			try {
//				if (null != rs)
//					rs.close();
//				if (null != st)
//					st.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//		return list;
//	}

//	public static <T> T pgQuery(String url, String user, String password, String sql, Function<ResultSet, T> func) {
//		Connection con = getConnection(url, user, password);
//		Statement st = null;
//		ResultSet rs = null;
//		T t = null;
//		try {
//			if (null != con) {
//				st = con.createStatement();
//				if (null != st) {
//					rs = st.executeQuery(sql);
//					if (null != rs && null != func) {
//						t = func.apply(rs);
//						rs.close();
//					}
//					st.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			Logger lgr = Logger.getLogger(PGHelper.class);
//			lgr.log(Level.ERROR, ex.getMessage(), ex);
//
//		} finally {
//			try {
//				if (null != rs)
//					rs.close();
//				if (null != st)
//					st.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//		return t;
//	}

//	public static void pgRun(String url, String user, String password, String sql) throws SQLException {
//		Connection con = getConnection(url, user, password);
//		Statement st = null;
//		try {
//			if (null != con) {
//				st = con.createStatement();
//				if (null != st) {
//					st.execute(sql);
//					st.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			Logger lgr = Logger.getLogger(PGHelper.class);
//			lgr.log(Level.ERROR, ex.getMessage(), ex);
//		} finally {
//			try {
//				if (null != st)
//					st.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//	}

	public String pgRun(String sql) {

		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {

			log.error( e.getMessage(), e);
		}
		Statement st = null;
		String strResult = "OK";
		try {
			String insertSql1 = sql.replace("'null'", "''");
			if (null != con) {
				st = con.createStatement();
				if (null != st) {
					//st.setFetchSize(100);  设置一次读取长度
					st.execute(insertSql1);
					st.close();
				}
				con.close();
			}
		} catch (Exception ex) {

			log.error(ex.getMessage(), ex);
			strResult = ex.getMessage();
		} finally {
			try {
				if (null != st)
					st.close();
				if (null != con)
					con.close();
			} catch (SQLException ex) {

				log.error(ex.getMessage(), ex);
				strResult = ex.getMessage();
			}
		}
		return strResult;

	}

//	public static void pgRunWithPara(String url, String user, String password, String sql,
//			Consumer<PreparedStatement> action) {
//		Connection con = getConnection(url, user, password);
//		PreparedStatement ps = null;
//		try {
//
//			if (null != con) {
//				ps = con.prepareStatement(sql);
//				if (null != ps && null != action) {
//					action.accept(ps);
//					ps.execute();
//					ps.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			Logger lgr = Logger.getLogger(PGHelper.class);
//			lgr.log(Level.ERROR, ex.getMessage(), ex);
//
//		} finally {
//			try {
//				if (null != ps)
//					ps.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//
//	}

//	public static void pgRunWithPara(String sql,
//			geovis.services.common.Consumer<Connection, PreparedStatement> action) {
//		Connection con = getConnection();
//		PreparedStatement ps = null;
//		try {
//			if (null != con) {
//				ps = con.prepareStatement(sql);
//				if (null != ps && null != action) {
//					action.accept(con, ps);
//					ps.execute();
//					ps.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			String msg = ex.getMessage();
//			if (!msg.contains("image_pkey")) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//
//		} finally {
//			try {
//				if (null != ps)
//					ps.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//
//	}

//	public static void pgRunWithPara(String sql, Consumer<PreparedStatement> action) {
//		Connection con = getConnection();
//		PreparedStatement ps = null;
//		try {
//			if (null != con) {
//				ps = con.prepareStatement(sql);
//				if (null != ps && null != action) {
//					action.accept(ps);
//					ps.execute();
//					ps.close();
//				}
//				con.close();
//			}
//		} catch (Exception ex) {
//			String msg = ex.getMessage();
//			if (!msg.contains("image_pkey")) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		} finally {
//			try {
//				if (null != ps)
//					ps.close();
//				if (null != con)
//					con.close();
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(PGHelper.class);
//				lgr.log(Level.ERROR, ex.getMessage(), ex);
//			}
//		}
//
//	}

	public <T> List<T> getListBySql(String sql, Supplier<T> getObj,
			lg.common.Consumer<ResultSet, T> action) {
		if (getObj == null || action == null)
			return null;

		List<T> result = pgQuery(sql, rs -> {
			try {
				ArrayList<T> list = new ArrayList<T>();
				while (rs.next()) {
					T t = getObj.get();
					action.accept(rs, t);
					list.add(t);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
		return result;
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

//	public static boolean existBySql(String sql) {
//		boolean result = PGHelper.pgQuery(sql, rs -> {
//			try {
//				while (rs.next()) {
//					return true;
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return false;
//		});
//		return result;
//	}

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

		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {

			log.error(e.getMessage(), e);
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

			log.error( ex.getMessage(), ex);

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
		return flag;
	}

}
