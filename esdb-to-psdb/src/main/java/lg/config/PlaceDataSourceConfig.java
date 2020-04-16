package lg.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@MapperScan(basePackages = "lg.dao.place", sqlSessionFactoryRef = "placeSqlSessionFactory")
public class PlaceDataSourceConfig {
    @Autowired
    private DbPgPlace dbPgTmp2;
    // 将这个对象放入Spring容器中
    @Bean(name = "placeDataSource")
    public DataSource getDateSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbPgTmp2.getDriverName());
        dataSource.setUrl(dbPgTmp2.getUrl());
        dataSource.setUsername(dbPgTmp2.getUsername());
        dataSource.setPassword(dbPgTmp2.getPassword());
        return dataSource;
    }
    @Bean(name = "placeSqlSessionFactory")
    // 表示这个数据源是默认数据源
    public SqlSessionFactory SqlSessionFactory(@Qualifier("placeDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
//        bean.setMapperLocations(
//                // 设置mybatis的xml所在位置
//                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }
    @Bean("placeSqlSessionTemplate")
    public SqlSessionTemplate sqlsessiontemplate(
            @Qualifier("placeSqlSessionFactory") SqlSessionFactory sessionfactory) {
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