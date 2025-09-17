package com.tuning;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan("com.tuning.mapper")
@SpringBootApplication(scanBasePackages = {"com.Tuning"})
public class SkyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkyServerApplication.class, args);
    log.info("Server Begin,Base url: http://localhost:8080/api/v1");
  }

}
