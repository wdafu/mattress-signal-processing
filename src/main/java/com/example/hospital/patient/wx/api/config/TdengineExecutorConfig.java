// package com.example.hospital.patient.wx.api.config;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @author GaoXin
// * @description
// * @date 2022/10/27
// */
//@Configuration
//@Slf4j
//public class TdengineExecutorConfig {
//    @Value("${tdengine.executor.thread.core_pool_size}")
//    private int corePoolSize;
//    @Value("${tdengine.executor.thread.max_pool_size}")
//    private int maxPoolSize;
//    @Value("${tdengine.executor.thread.queue_capacity}")
//    private int queueCapacity;
//    @Value("${tdengine.executor.thread.name.prefix}")
//    private String namePrefix;
//
//    @Bean(name = "tdWriteExecutor")
//    public Executor tdWriteExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setThreadNamePrefix(namePrefix);
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.setAwaitTerminationSeconds(60);
//        executor.initialize();
//        log.info("start tdWriteExecutor success");
//        return executor;
//    }
//}