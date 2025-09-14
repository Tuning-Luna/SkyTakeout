package com.Tuning.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeLoginVO {
  private Integer id;          // 主键
  private String name;      // 员工姓名
  private String userName;  // 登录用户名
  private String token;     // JWT 令牌
}
