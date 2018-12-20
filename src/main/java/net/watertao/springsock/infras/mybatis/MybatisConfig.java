package net.watertao.springsock.infras.mybatis;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.htsec.smsassist.mapper"})
public class MybatisConfig {

    @Autowired
    private Environment env;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-packag"));
        sessionFactory .setMapperLocations(new PathMatchingResourcePatternResolver()
                        .getResources(env.getProperty("mybatis.mapper-locations")));
        sessionFactory
                .setConfigLocation(new DefaultResourceLoader()
                        .getResource(env.getProperty("mybatis.config-location")));
        return sessionFactory.getObject();

    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(env.getProperty("spring.dataSource.url"));
        datasource.setUsername(env.getProperty("spring.dataSource.username"));
        datasource.setPassword(env.getProperty("spring.dataSource.password"));
        datasource.setDriverClassName(env.getProperty("spring.dataSource.driver"));
        datasource.setInitialSize(0);
        datasource.setMinIdle(0);
        datasource.setMaxActive(Integer.parseInt(env.getProperty("spring.dataSource.maxActive")));
        datasource.setMaxWait(6000l);
        datasource.setValidationQuery("select 1 from sysibm.sysdummy1");
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTestWhileIdle(true);
        datasource.setTimeBetweenEvictionRunsMillis(60000l);
        datasource.setMinEvictableIdleTimeMillis(25200000l);
        datasource.setRemoveAbandoned(true);
        datasource.setRemoveAbandonedTimeout(1800);
        datasource.setLogAbandoned(true);

        return datasource;
    }


}
