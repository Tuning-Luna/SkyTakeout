package com.Tuning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePasswordUpdateDTO {

  @NotNull(message = "员工ID不能为空")
  private Long empId;

  @NotBlank(message = "旧密码不能为空")
  private String oldPassword;

  @NotBlank(message = "新密码不能为空")
  private String newPassword;
}