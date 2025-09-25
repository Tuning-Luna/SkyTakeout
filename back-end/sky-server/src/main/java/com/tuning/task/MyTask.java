package com.tuning.task;

import org.springframework.stereotype.Component;

@Component
public class MyTask {

  // 每隔 5 秒执行一次
  // 格式: 秒 分 时 日 月 星期
  // @Scheduled(cron = "*/5 * * * * ?")
  public void printMessage() {
    System.out.println("Hello, cron 表达式每隔5秒执行：" + System.currentTimeMillis());
  }
}