package com.Tuning.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsSalesDTO {
  // 商品名称
  private String name;
  // 销量
  private Integer number;
}
