package com.tuning.service.impl;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.CategoryCreateDTO;
import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.dto.CategoryUpdateDTO;
import com.Tuning.entity.Category;
import com.Tuning.exception.BizException;
import com.Tuning.result.PageResult;
import com.Tuning.vo.CategoryQueryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.CategoryMapper;
import com.tuning.mapper.DishMapper;
import com.tuning.mapper.SetmealMapper;
import com.tuning.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  final private CategoryMapper categoryMapper;

  final private DishMapper dishMapper;

  final private SetmealMapper setmealMapper;

  @Autowired
  public CategoryServiceImpl(CategoryMapper categoryMapper,
                             DishMapper dishMapper,
                             SetmealMapper setmealMapper) {
    this.categoryMapper = categoryMapper;
    this.dishMapper = dishMapper;
    this.setmealMapper = setmealMapper;
  }

  @Override
  public void addCategory(CategoryCreateDTO dto) {
    Category category = new Category();

    BeanUtils.copyProperties(dto, category);

    category.setId(null);
    category.setCreateUser(BaseContext.getCurrentId());
    category.setUpdateUser(BaseContext.getCurrentId());
    category.setStatus(1); // 默认启用

    Integer rows = categoryMapper.insertCategory(category);
    if (rows != 1) {
      throw new BizException(HttpStatus.BAD_REQUEST, "添加失败");
    }
  }

  @Override
  public PageResult<CategoryQueryVO> page(CategoryPageQueryDTO dto) {
    PageHelper.startPage(dto.getPage(), dto.getPageSize());

    Page<CategoryQueryVO> page = categoryMapper.selectPage(dto);

    long total = page.getTotal();
    List<CategoryQueryVO> result = page.getResult();

    return new PageResult<>(total, result);
  }

  @Override
  public void deleteById(Long id) {
    // 1. 检查该分类下是否有菜品
    Integer dishCount = dishMapper.countByCategoryId(id);
    System.out.println(dishCount);
    if (dishCount > 0) {
      throw new BizException(HttpStatus.BAD_REQUEST, "该分类下有菜品，无法删除");
    }

    // 2. 检查该分类下是否有套餐
    Integer setmealCount = setmealMapper.countByCategoryId(id);
    System.out.println(setmealCount);
    if (setmealCount > 0) {
      throw new BizException(HttpStatus.BAD_REQUEST, "该分类下有套餐，无法删除");
    }

    // 3. 删除分类
    Integer deleted = categoryMapper.deleteById(id);
    if (deleted <= 0) {
      throw new BizException(HttpStatus.BAD_REQUEST, "删除失败");
    }
  }

  @Override
  public CategoryQueryVO selectById(Long id) {
    Category entity = categoryMapper.selectById(id);
    CategoryQueryVO vo = new CategoryQueryVO();
    BeanUtils.copyProperties(entity, vo);
    return vo;
  }

  @Override
  public void update(CategoryUpdateDTO dto) {
    Category originEntity = categoryMapper.selectById(dto.getId());
    if (originEntity == null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "要更新的数据不存在");
    }

    Category category = new Category();
    BeanUtils.copyProperties(dto, category);

    // 设置修改时间，修改人
    category.setUpdateTime(LocalDateTime.now());
    category.setUpdateUser(BaseContext.getCurrentId());

    categoryMapper.update(category);
  }

  @Override
  public void startOrStop(Integer status, Long id) {
    if (status == null || (status != 0 && status != 1)) {
      throw new BizException(HttpStatus.BAD_REQUEST, "状态值不合法");
    }


    Category originEntity = categoryMapper.selectById(id);
    if (originEntity == null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "要更新的数据不存在");
    }

    Category category = new Category();
    category.setId(id);
    category.setStatus(status);

    // 设置修改时间，修改人
    category.setUpdateTime(LocalDateTime.now());
    category.setUpdateUser(BaseContext.getCurrentId());

    categoryMapper.update(category);
  }

}
