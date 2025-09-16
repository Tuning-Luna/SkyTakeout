package com.Tuning.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

  // id 不允许前端传递，如果传递了也要重置，所以这里不用加校验
  private Long id;

  @NotBlank(message = "分类名称不能为空")
  @Size(max = 32, message = "分类名称长度不能超过32个字符")
  private String name;

  @NotNull(message = "排序值不能为空")
  @Min(value = 0, message = "排序值不能小于0")
  private Integer sort;

  @NotNull(message = "分类类型不能为空")
  @Min(value = 1, message = "分类类型不合法")
  @Max(value = 2, message = "分类类型不合法")
  private Integer type; // 1 菜品分类, 2 套餐分类
}
