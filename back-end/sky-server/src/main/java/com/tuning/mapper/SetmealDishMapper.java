package com.tuning.mapper;

import com.Tuning.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
  List<Long> getSetmealDishByDishIds(List<Long> dishIds);

  void insertBatch(List<SetmealDish> setmealDishes);

  void deleteBySetmealid(Integer setmealId);

  List<SetmealDish> getBySetmealId(Long setmealId);
}
