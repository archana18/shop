package com.example.demo;







import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication(scanBasePackages={"com.example.demo"})
//for external file 
@EnableAutoConfiguration(exclude = {
		 ErrorMvcAutoConfiguration.class
		})
@PropertySource(value = "classpath:application.properties")
@ComponentScan("com.example.demo")
public class SpringbootJspApplication extends SpringBootServletInitializer  {
	
	
	

	@Value("${spring.datasource.driver-class-name}")
	private String className;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String dialect;
	@Value("${spring.jpa.show-sql}")
	private String show_sql;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hbm2ddl;
	
	
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootJspApplication.class);
    }


	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJspApplication.class, args);

	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(className);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;

	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.example.demo");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", dialect);
		hibernateProperties.put("hibernate.show_sql", show_sql);
		hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}
}


