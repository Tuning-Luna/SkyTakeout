package com.Tuning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishPageQueryDTO {
  private String categoryId;

  private String name;

  private Integer page;

  private Integer pageSize;

  private Integer status;
}
