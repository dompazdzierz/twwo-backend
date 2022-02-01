package pl.pwr.dissertation.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.pwr.dissertation.persistance.entity.UserEntity;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = {"pl.pwr.dissertation.persistance.repository"}
)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class HibernateConfig {

    @Value("${spring.mysql.host}")
    private String mysqlHost;

    @Value("${spring.mysql.login}")
    private String mysqlUser;

    @Value("${spring.mysql.pass}")
    private String mysqlPass;

    @Value("${spring.mysql.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.mysql.dialect}")
    private String dialect;

    @Value("${spring.mysql.driverClassName}")
    private String driverClassName;

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("pl.pwr.dissertation.persistance.entity")
                .properties(hibernatePropertiesStringMap())
                .build();
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("pl.pwr.dissertation.persistance.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setImplicitNamingStrategy(SpringImplicitNamingStrategy.INSTANCE);
        sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        return sessionFactory;
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(mysqlHost);
        dataSource.setUsername(mysqlUser);
        dataSource.setPassword(mysqlPass);
        dataSource.setConnectionProperties(hibernateProperties());
        return dataSource;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> hibernatePropertiesStringMap() {
        Properties p = hibernateProperties();
        return p.stringPropertyNames().stream().collect(Collectors.toMap(k -> k, k -> p.getProperty(k)));
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        hibernateProperties.setProperty("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());

        return hibernateProperties;
    }

    @Bean
    AuditorAware<UserEntity> auditorAware() {
        return new AuditorAwareImpl();
    }
}
