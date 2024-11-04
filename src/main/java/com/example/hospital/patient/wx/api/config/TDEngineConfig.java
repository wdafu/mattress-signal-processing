//package com.example.hospital.patient.wx.api.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = {"com.example.hospital.patient.wx.api.db.dao.td",}, sqlSessionFactoryRef = "tdengineSessionFactory")
//public class TDEngineConfig {
//
//    @Bean(name = "tdengineDataSource")
//    public DataSource mysqlDataSourceProperties(@Autowired TdengineConfigProperties tdengineConfigProperties) {
//        HikariConfig config = new HikariConfig();
//        config.setPoolName("DroneTdengineHikariPool");
//        config.setDriverClassName(tdengineConfigProperties.getDriverClassName());
//        config.setUsername(tdengineConfigProperties.getUsername());
//        config.setPassword(tdengineConfigProperties.getPassword());
//        config.setJdbcUrl(tdengineConfigProperties.getJdbcUrl());
//        config.setMinimumIdle(tdengineConfigProperties.getMinimumIdle());
//        config.setIdleTimeout(tdengineConfigProperties.getIdleTimeout());
//        config.setMaximumPoolSize(tdengineConfigProperties.getMaximumPoolSize());
//        config.setMaxLifetime(tdengineConfigProperties.getMaxLifeTime());
//        config.setConnectionTimeout(tdengineConfigProperties.getConnectionTimeout());
//        config.setConnectionTestQuery("select server_status()");
//        return new HikariDataSource(config);
//    }
//
//    @Bean(name = "tdengineSessionFactory")
//    public SqlSessionFactory mysqlSessionFactory(@Qualifier("tdengineDataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setDefaultStatementTimeout(1);
//        bean.setConfiguration(configuration);
//        return bean.getObject();
//    }
//
//    @Bean(name = "tdengineSessionTemplate")
//    public SqlSessionTemplate
//    sqlSessionTemplate(@Qualifier("tdengineSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
