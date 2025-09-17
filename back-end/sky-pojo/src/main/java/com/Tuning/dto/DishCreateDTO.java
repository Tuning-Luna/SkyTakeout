package com.Tuning.dto;

import com.Tuning.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishCreateDTO {
  private Long categoryId;

  private String description;

  private Long id; // 前端不用传递

  private String image;

  private String name;

  private Integer price;

  private Integer status; // 不用传递，默认是1（启用）。0（禁用）

  List<DishFlavor> flavors;
}
