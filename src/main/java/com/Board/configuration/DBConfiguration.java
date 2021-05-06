package com.Board.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	// spring container Bean의 생성 관리

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	// @PropertySource("classpath:/application.properties")의 ~hikari로 시작하는 모든 설정을
	// 매핑한다.
	// hikariconfig DB연결 라이브러리 중 하나
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	// data source - db 연결을 위한 인터페이스
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	// jsp 설정 명시
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
