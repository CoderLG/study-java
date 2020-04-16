package lg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager",
        basePackages = {"lg.dao.db1"})//对应数据源的Repository的包目录

public class MasterConfig {
    @Autowired
    private HibernateProperties hibernateProperties;

    @Resource
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    @Primary
    @Bean(name = "masterEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return masterEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Resource
    private JpaProperties jpaProperties;

//    private Map<String, Object> getVendorProperties() {
//        return jpaProperties.getHibernateProperties(new HibernateSettings());
//    }

    /**
     * 设置实体类所在位置
     */
    @Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder) {

        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(masterDataSource)
                .packages("lg.domain.db1")//对应数据库的实体类Entity包目录
                .persistenceUnit("masterPersistenceUnit")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "masterTransactionManager")
    public PlatformTransactionManager masterTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(masterEntityManagerFactory(builder).getObject());
    }
}