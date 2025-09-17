package com.Tuning.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageQueryDTO {

  private String name;

  @NotNull(message = "页码不能为空")
  @Min(value = 1, message = "页码必须大于等于1")
  private Integer page;

  @NotNull(message = "每页大小不能为空")
  @Min(value = 1, message = "每页大小必须大于等于1")
  private Integer pageSize;

  private Integer type;
}
