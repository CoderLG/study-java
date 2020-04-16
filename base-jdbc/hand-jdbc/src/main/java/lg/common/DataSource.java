package lg.common;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * author: LG
 * date: 2019-09-04 20:36
 * desc:
 */
@Configuration
@Slf4j
public class DataSource {

    @Value("${Url}")
    private  String url;
    @Value("${Driver}")
    private  String driver;
    @Value("${PgUsername}")
    private  String user;
    @Value("${Password}")
    private  String password;


    @Bean
    public DruidDataSource getInstance(){
        log.info("加载DruidDataSource");
        DruidDataSource instance = new DruidDataSource();
        instance.setDriverClassName(driver);
        instance.setUrl(url);
        instance.setPassword(password);
        instance.setUsername(user);
        instance.setInitialSize(5);
        instance.setMinIdle(4);
        instance.setMaxActive(16);
        instance.setMaxWait(60000);
        instance.setTimeBetweenEvictionRunsMillis(60000);
        instance.setMinEvictableIdleTimeMillis(30000);
        return instance;
    }
}
