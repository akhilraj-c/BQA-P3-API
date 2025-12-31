package com.mindteck.db_config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.mindteck.repository_cas",
        entityManagerFactoryRef = "casEntityManagerFactory",
        transactionManagerRef = "casTransactionManager"
)
public class SecondaryDatabaseConfig {

    @Bean(name = "casDataSource")
    @ConfigurationProperties(prefix = "cas.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "casEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("casDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.mindteck.models_cas")
                .persistenceUnit("cas")
                .build();
    }

    @Bean(name = "casTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("casEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
