package com.tuning.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

  // 传入category的id，统计此分类下菜品数据
  Integer countByCategoryId(Long categoryId);
}
