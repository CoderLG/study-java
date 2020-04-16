package lg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "entityManagerFactorySecondary",
        transactionManagerRef = "transactionManagerSecondary",
        basePackages = {"lg.dao.db2"})
public class JpaSecondaryConfig {


    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    private JpaProperties jpaProperties;


    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;


    @Bean(name="entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder){
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder
                .dataSource(secondaryDataSource)
                .packages("lg.domain.db2")
                .persistenceUnit("secondaryPersistenceUnit")
                .properties(properties)
                .build();

    }

    @Bean(name="entityManagerSecondary")
    public EntityManager entityManagerSecondary(EntityManagerFactoryBuilder builder){
        return entityManagerFactorySecondary(builder).getObject().createEntityManager();
    }


    @Bean(name = "transactionManagerSecondary")
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
    }



}
