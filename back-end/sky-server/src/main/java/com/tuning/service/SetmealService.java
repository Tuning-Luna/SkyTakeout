package com.tuning.service;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.SetmealVO;

public interface SetmealService {
  void insertWithDish(SetmealCreateDTO dto);

  PageResult<SetmealVO> page(SetmealPageQueryDTO dto);
}
