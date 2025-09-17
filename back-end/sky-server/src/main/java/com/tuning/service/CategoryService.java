package com.tuning.service;

import com.Tuning.dto.CategoryDTO;
import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.CategoryQueryVO;
import jakarta.validation.Valid;

public interface CategoryService {
  void addCategory(@Valid CategoryDTO dto);

  PageResult<CategoryQueryVO> page(@Valid CategoryPageQueryDTO dto);

  void deleteById(Long id);
}
