package com.tuning.service;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.dto.SetmealUpdateDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
  void insertWithDish(SetmealCreateDTO dto);

  PageResult<SetmealVO> page(SetmealPageQueryDTO dto);

  void deleteBatch(List<Integer> ids);

  void update(SetmealUpdateDTO dto);

  SetmealVO getByIdWithDish(Long id);
}
