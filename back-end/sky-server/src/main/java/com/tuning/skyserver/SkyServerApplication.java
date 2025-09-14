package com.tuning.skyserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SkyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkyServerApplication.class, args);
    log.info("Server start,request base url : http://localhost:8080/api/v1");
  }

}
