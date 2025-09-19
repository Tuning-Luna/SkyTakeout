package com.Tuning.dto;

import com.Tuning.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetmealUpdateDTO {
  private Long categoryId;

  private String description;

  private Long id; // optional

  private String image;

  private String name;

  private BigDecimal price;

  private Integer status; // optional,default 0

  private List<SetmealDish> setmealDishes;
}
