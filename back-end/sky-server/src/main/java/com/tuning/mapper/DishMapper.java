package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.entity.Dish;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {

  // 传入category的id，统计此分类下菜品数据
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  Integer insert(Dish dish);
}
