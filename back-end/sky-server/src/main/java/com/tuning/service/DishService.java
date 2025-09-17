package com.tuning.service;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishPageQueryVO;

public interface DishService {
  void insertWithFlavor(DishCreateDTO dto);

  PageResult<DishPageQueryVO> pageQuery(DishPageQueryDTO dto);
}
