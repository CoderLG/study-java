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

/**
 * author: LG
 * date: 2019-10-30 15:44
 * desc:
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"lg.dao.db1"})
public class JpaPrimaryConfig {


    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;




    @Primary
    @Bean(name="entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder){
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder
                .dataSource(primaryDataSource)
                .packages("lg.domain.db1")
                .persistenceUnit("primaryPersistenceUnit")
                .properties(properties)
                .build();

    }


    @Primary
    @Bean(name="entityManagerPrimary")
    public EntityManager entityManagerPrimary(EntityManagerFactoryBuilder builder){
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }



    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }



}
