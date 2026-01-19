package riconciliazionetitoli;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "riconciliazionetitoli.mapper" }, sqlSessionTemplateRef = "h14SqlSessionTemplate")
@PropertySource("classpath:application.properties")
public class DbConfig {
	
	@Value("${mybatis.mappers}")
	private String mybatisMappers;
	@Autowired
	private Environment env;
	
	@Bean(name = "h14DataSource")
	DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.url"));
		dataSource.setUrl(env.getProperty("spring.datasource.driverClassName"));
		return dataSource;
	}
	
	@Bean(name = "h14SqlSessionFactory")
	SqlSessionFactory sqlSessionFactory(@Qualifier("h14DataSource") DataSource dataSource, ObjectProvider<Interceptor[]> interceptorsProvider) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + mybatisMappers));
		interceptorsProvider.ifAvailable(factoryBean::setPlugins);
		
		factoryBean.afterPropertiesSet();
		SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
		sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);
		
		return sqlSessionFactory;
	}
	
	@Bean(name = "h14SqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate(@Qualifier("h14SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name = "h14SqlSessionFactory")
	DataSourceTransactionManager transactionManager(@Qualifier("h14DataSource") DataSource h14DataSource) {
		return new DataSourceTransactionManager(h14DataSource);
	}
	
	@Bean
	@DependsOn("h14DataSource")
	SpringLiquibase liquibase(DataSource clvdbDataSource) {
		log.info("loading h14db");
	
	SpringLiquibase liquibase = new SpringLiquibase();
	liquibase.setChangeLog("classpath:liquibase/h14Liquibase-changeLog.xml");
		liquibase.setDataSource(clvdbDataSource);
		return liquibase;
	}
}
