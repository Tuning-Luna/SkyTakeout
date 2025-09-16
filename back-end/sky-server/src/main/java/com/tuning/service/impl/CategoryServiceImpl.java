package com.tuning.service.impl;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.CategoryDTO;
import com.Tuning.entity.Category;
import com.Tuning.exception.BizException;
import com.tuning.mapper.CategoryMapper;
import com.tuning.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  final private CategoryMapper categoryMapper;

  @Autowired
  public CategoryServiceImpl(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  @Override
  public void addCategory(CategoryDTO dto) {
    Category category = new Category();

    BeanUtils.copyProperties(dto, category);

    category.setId(null);
    category.setCreateUser(BaseContext.getCurrentId());
    category.setUpdateUser(BaseContext.getCurrentId());
    category.setStatus(1); // 默认启用

    Integer rows = categoryMapper.insertCategory(category);
    if (rows != null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "添加失败");
    }
  }
}
