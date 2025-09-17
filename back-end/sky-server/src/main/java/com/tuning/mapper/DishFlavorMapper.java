package com.tuning.mapper;

import com.Tuning.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
  Integer insert(DishFlavor flavor);

  // 批量插入
  Integer insertBatch(@Param("flavors") List<DishFlavor> flavors);

  void deleteByDishId(Long dishId);
}
