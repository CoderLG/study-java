package lg.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * author: LG
 * date: 2019-09-11 10:19
 * desc:
 */

//表示这个类为一个配置类
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "lg.dao.dao1", sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSourceConfig1 {
    @Autowired
    private DbPgTmp1 dbPgTmp1;
    // 将这个对象放入Spring容器中
    @Bean(name = "test1DataSource")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    public DataSource getDateSource1() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbPgTmp1.getDriverName());
        dataSource.setUrl(dbPgTmp1.getUrl());
        dataSource.setUsername(dbPgTmp1.getUsername());
        dataSource.setPassword(dbPgTmp1.getPassword());
        return dataSource;
    }
    @Bean(name = "test1SqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
//        bean.setMapperLocations(
//                // 设置mybatis的xml所在位置
//                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }
    @Bean("test1SqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate test1sqlsessiontemplate(
            @Qualifier("test1SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

//    @Bean("test1Transaction")
//    // 表示这个数据源是默认数据源
//    @Primary
//    public DataSourceTransactionManager test1Transaction(
//            @Qualifier("test1DataSource") DataSource datasource) {
//        return new DataSourceTransactionManager(datasource);
//    }
}