package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.entity.Dish;
import com.Tuning.vo.DishPageQueryVO;
import com.github.pagehelper.Page;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {

  // 传入category的id，统计此分类下菜品数据
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  Integer insert(Dish dish);

  Page<DishPageQueryVO> pageQuery(DishPageQueryDTO dto);

  Dish getById(Long id);

  void deleteById(Long id);
}
