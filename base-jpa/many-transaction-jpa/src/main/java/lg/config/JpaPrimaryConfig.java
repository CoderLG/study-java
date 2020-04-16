package lg.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
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
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.util.HashMap;

/**
 * author: LG
 * date: 2019-10-31 10:34
 * desc:
 */
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "transactionManager",
        basePackages = {"lg.dao.db1"})
public class JpaPrimaryConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Primary
    @Bean(name = "primaryDataSourceProperties")
    @Qualifier("primaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "primaryDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(){
        PGXADataSource dataSource = new PGXADataSource();
        dataSource.setUrl(primaryDataSourceProperties().getUrl());
        dataSource.setPassword(primaryDataSourceProperties().getPassword());
        dataSource.setUser(primaryDataSourceProperties().getUsername());

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("primary");
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);
        atomikosDataSourceBean.setMaxPoolSize(20);
        return atomikosDataSourceBean;
    }


    @Primary
    @Bean(name = "primaryEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean primaryEntityManager(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("hibernate.transaction.jta.platform",AtomikosJtaPlatform.class.getName());
        map.put("javax.persistence.transactionType","JTA");
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJtaDataSource(primaryDataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        localContainerEntityManagerFactoryBean.setPackagesToScan("lg.domain.db1");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("primaryPersistenceUnit");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(map);

        return localContainerEntityManagerFactoryBean;


    }

}
