package com.Tuning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "用户名不能为空")
  private String username;

  @NotBlank(message = "用户名不能为空")
  private String name;

  @Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
  private String phone;

  @Size(max = 1, message = "性别长度不正确")
  private String sex;

  @NotBlank(message = "身份证号不能为空")
  private String idNumber;

}
