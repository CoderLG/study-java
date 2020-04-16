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
@MapperScan(basePackages = "lg.dao.dao2", sqlSessionFactoryRef = "test2SqlSessionFactory")
public class DataSourceConfig2 {
    @Autowired
    private DbPgTmp2 dbPgTmp2;
    // 将这个对象放入Spring容器中
    @Bean(name = "test2DataSource")
    public DataSource getDateSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbPgTmp2.getDriverName());
        dataSource.setUrl(dbPgTmp2.getUrl());
        dataSource.setUsername(dbPgTmp2.getUsername());
        dataSource.setPassword(dbPgTmp2.getPassword());
        return dataSource;
    }
    @Bean(name = "test2SqlSessionFactory")
    // 表示这个数据源是默认数据源
    public SqlSessionFactory SqlSessionFactory(@Qualifier("test2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
//        bean.setMapperLocations(
//                // 设置mybatis的xml所在位置
//                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }
    @Bean("test2SqlSessionTemplate")
    public SqlSessionTemplate sqlsessiontemplate(
            @Qualifier("test2SqlSessionFactory") SqlSessionFactory sessionfactory) {
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