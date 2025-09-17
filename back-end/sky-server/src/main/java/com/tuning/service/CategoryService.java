package com.tuning.service;

import com.Tuning.dto.CategoryCreateDTO;
import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.dto.CategoryUpdateDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.CategoryQueryVO;
import jakarta.validation.Valid;

public interface CategoryService {
  void addCategory(@Valid CategoryCreateDTO dto);

  PageResult<CategoryQueryVO> page(@Valid CategoryPageQueryDTO dto);

  void deleteById(Long id);

  CategoryQueryVO selectById(Long id);

  void update(@Valid CategoryUpdateDTO dto);

  void startOrStop(Integer status, Long id);
}
