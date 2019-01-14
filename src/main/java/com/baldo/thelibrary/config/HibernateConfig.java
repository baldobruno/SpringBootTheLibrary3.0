package com.baldo.thelibrary.config;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan({"com.baldo.thelibrary.config"})
@PropertySource("classpath:application.properties")

public class HibernateConfig {

	@Autowired Environment env;  //con questa classe accederemo alle variabili di configurazione
	@Autowired  DataSource dataSource;  //classe di configurazione fonte dati
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	factory.setJpaVendorAdapter(this.jpaVendorAdapter());
	factory.setDataSource(this.dataSource);
	factory.setPackagesToScan("com.baldo.thelibrary.domain");
	factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
	factory.setValidationMode(ValidationMode.NONE);
	factory.setJpaProperties(this.hibernateProperties());
	return factory;
	}
	
	@Bean //(name = "dataSource")   //connessione fonte dati
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();	
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		
		return dataSource;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
		
		return hibernateJpaVendorAdapter;
	}
	
	@Bean   //specifiche inserite nell'application properties 
	public Properties hibernateProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.schema-generation.database.action", "none");
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		// Setting C3P0 properties
//		jpaProperties.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
//		jpaProperties.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
//		jpaProperties.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
//		jpaProperties.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
//		jpaProperties.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
		
		return jpaProperties;
	}
	
	@Bean   //gestisce le trasazioni al momento di aggiornamento o eliminazione dati
	public JpaTransactionManager transactionManager()
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
	
}
