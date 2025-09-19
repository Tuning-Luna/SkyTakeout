package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.entity.Setmeal;
import com.Tuning.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

  // 传入category的id，统计此分类下菜品数据
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  void insert(Setmeal setmeal);

  Page<SetmealVO> pageQuery(SetmealPageQueryDTO dto);
}
