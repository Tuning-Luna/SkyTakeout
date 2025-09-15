package com.Tuning.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePageQueryDTO {
  private String name; // 筛选用的，非必须

  @NotNull(message = "页码不能为空")
  @Min(value = 1, message = "页码必须大于等于1")
  private Integer page;

  @NotNull(message = "每页条数不能为空")
  @Min(value = 1, message = "每页条数必须大于等于1")
  private Integer pageSize;
}
