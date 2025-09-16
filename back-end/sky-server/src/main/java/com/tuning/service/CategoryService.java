package com.tuning.service;

import com.Tuning.dto.CategoryDTO;
import jakarta.validation.Valid;

public interface CategoryService {
  void addCategory(@Valid CategoryDTO dto);
}
