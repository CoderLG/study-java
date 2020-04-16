package lg.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * author: LG
 * date: 2019-10-31 10:34
 * desc:
 */
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "transactionManager",
        basePackages = {"lg.dao.db2"})
public class JpaSecondaryConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;


    @Bean(name = "secondaryDataSourceProperties")
    @Qualifier("secondaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(name = "secondaryDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource(){
        PGXADataSource dataSource = new PGXADataSource();
        dataSource.setUrl(secondaryDataSourceProperties().getUrl());
        dataSource.setPassword(secondaryDataSourceProperties().getPassword());
        dataSource.setUser(secondaryDataSourceProperties().getUsername());

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("secondary");
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);
        atomikosDataSourceBean.setMaxPoolSize(20);
        return atomikosDataSourceBean;
    }



    @Bean(name = "secondaryEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManager(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("hibernate.transaction.jta.platform",AtomikosJtaPlatform.class.getName());
        map.put("javax.persistence.transactionType","JTA");
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJtaDataSource(secondaryDataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        localContainerEntityManagerFactoryBean.setPackagesToScan("lg.domain.db2");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("secondaryPersistenceUnit");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(map);

        return localContainerEntityManagerFactoryBean;


    }

}
