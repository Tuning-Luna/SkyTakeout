package com.Tuning.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOverViewVO {
  // 已启售数量
  private Integer sold;

  // 已停售数量
  private Integer discontinued;
}
