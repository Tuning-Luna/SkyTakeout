package com.tuning.service;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishPageQueryVO;
import com.Tuning.vo.DishVO;

import java.util.List;

public interface DishService {
  void insertWithFlavor(DishCreateDTO dto);

  PageResult<DishPageQueryVO> pageQuery(DishPageQueryDTO dto);

  void deleteBatch(List<Long> ids);

  DishVO getWithFlavorById(Long id);
}
