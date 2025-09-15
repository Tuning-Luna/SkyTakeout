package com.Tuning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeCreateDTO {
  // id前端不应该传递，传递了也会重置为null，由数据库自动递增，为了保证接口文档一致加上此字段
  private Integer id;

  private String username;

  private String name;

  private String phone;

  private String sex;

  private String idNumber;

}
