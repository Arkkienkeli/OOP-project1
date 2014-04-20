package com.webcontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.sql.DataSource;
import java.util.List;
import org.hibernate.SessionFactory;
import java.util.Properties;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;



@Configuration
@EnableWebMvc
@ComponentScan("com.webcontroller.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(
        List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new JsonMessageConverter());
    }
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds =
                new SimpleDriverDataSource(org.h2.Driver.load(), "jdbc:h2/tmp/testdb", "sa", "sa");
                return ds;
    }
    @Bean
    @DependsOn("dataSource")
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.webcontroller.entity");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }
    private Properties hibernateProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        p.setProperty("hiberntate.hm2ddl.auto", "update");
        return p;
    }
    @Bean
    @DependsOn("sessionFactoryBean")
    public PlatformTransactionManager transactionManager() {
        SessionFactory sessionFactory = sessionFactoryBean().getObject();
        HibernateTransactionManager tm = new HibernateTransactionManager(sessionFactory);
        return tm;
    }
}