package com.tuning;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@MapperScan("com.tuning.mapper")
@SpringBootApplication(scanBasePackages = {"com.Tuning"})
@EnableTransactionManagement // 开始注解方式的事务管理
@EnableCaching // 开启springboot 注解
@EnableScheduling // 开启定时任务支持
public class SkyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkyServerApplication.class, args);
    log.info("Server Begin,Base url: http://localhost:8080/api/v1");
  }

}
