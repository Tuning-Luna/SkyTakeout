package com.Tuning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {
  @NotNull(message = "id不能为空")
  private Long id;

  @NotBlank(message = "用户名不能为空")
  private String username;

  @NotBlank(message = "名字不能为空")
  private String name;

  @Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
  private String phone;

  @Size(max = 1, message = "性别长度不正确")
  private String sex;

  @NotBlank(message = "身份证号不能为空")
  private String idNumber;
}
