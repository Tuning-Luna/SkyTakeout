package com.tuning;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@MapperScan("com.tuning.mapper")
@SpringBootApplication(scanBasePackages = {"com.Tuning"})
@EnableTransactionManagement // 开始注解方式的事务管理
public class SkyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkyServerApplication.class, args);
    log.info("Server Begin,Base url: http://localhost:8080/api/v1");
  }

}
