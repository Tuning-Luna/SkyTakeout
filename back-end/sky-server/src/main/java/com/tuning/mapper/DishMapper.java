package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.entity.Dish;
import com.Tuning.vo.DishListVO;
import com.Tuning.vo.DishPageQueryVO;
import com.github.pagehelper.Page;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

  // 传入category的id，统计此分类下菜品数据
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  Integer insert(Dish dish);

  Page<DishPageQueryVO> pageQuery(DishPageQueryDTO dto);

  Dish getById(Long id);

  void deleteById(Long id);

  @AutoFill(value = OperationType.UPDATE)
  void update(Dish dish);

  List<DishListVO> getByCategoryId(Long categoryId);

  List<Dish> getBySetmealId(Long setmealId);

  List<Dish> list(Dish dish);

  Integer countByMap(Map<String, Object> map);
}
