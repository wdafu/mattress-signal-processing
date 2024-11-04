//package com.example.hospital.patient.wx.api.config;
//
//
//import com.example.hospital.patient.wx.api.util.TDengineClientUtil;
//import lombok.Data;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
///**
// * @author GaoXin
// * @description
// * @date 2022/10/21
// */
//@Component
//@Data
//public class TdengineConfigProperties implements InitializingBean {
//
//    @Autowired
//    private Environment env;
//
//    @Value("${spring.datasource.tdengine.username}")
//    String username;
//    @Value("${spring.datasource.tdengine.password}")
//    String password;
//    @Value("${spring.datasource.tdengine.jdbc-url}")
//    String jdbcUrl;
//    @Value("${spring.datasource.tdengine.minimum-idle}")
//    int minimumIdle;
//    @Value("${spring.datasource.tdengine.maximum-pool-size}")
//    int maximumPoolSize;
//    @Value("${spring.datasource.tdengine.idle-timeout}")
//    int idleTimeout;
//    @Value("${spring.datasource.tdengine.max-lifetime}")
//    int maxLifeTime;
//    @Value("${spring.datasource.tdengine.connection-timeout}")
//    int connectionTimeout;
//    @Value("${spring.datasource.tdengine.driver-class-name}")
//    String driverClassName;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        if (!"localhost".equals(env)) {
//            TDengineClientUtil.installClient();
//        }
//    }
//
//    public String getEnv() {
//        return env.getActiveProfiles()[0];
//    }
//
//}