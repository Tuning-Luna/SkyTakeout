package com.tuning.service.impl;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.entity.Dish;
import com.Tuning.entity.DishFlavor;
import com.Tuning.exception.BizException;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishPageQueryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.DishFlavorMapper;
import com.tuning.mapper.DishMapper;
import com.tuning.mapper.SetmealDishMapper;
import com.tuning.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

  final private DishMapper dishMapper;

  final private DishFlavorMapper dishFlavorMapper;

  final private SetmealDishMapper setmealDishMapper;

  @Autowired
  public DishServiceImpl(DishMapper dishMapper,
                         DishFlavorMapper dishFlavorMapper,
                         SetmealDishMapper setmealDishMapper) {
    this.dishMapper = dishMapper;
    this.dishFlavorMapper = dishFlavorMapper;
    this.setmealDishMapper = setmealDishMapper;
  }

  @Override
  @Transactional
  public void insertWithFlavor(DishCreateDTO dto) {
    // 插入一个菜品
    Dish dish = new Dish();

    BeanUtils.copyProperties(dto, dish);
    dish.setPrice(dto.getPrice() != null ? BigDecimal.valueOf(dto.getPrice()) : null);
    dish.setCategoryId(1L); // 菜品对应的id就是1，以防传入其他的值

    dish.setId(null);
    dish.setStatus(1);

    Integer row = dishMapper.insert(dish);

    if (row != 1) {
      throw new BizException(HttpStatus.BAD_REQUEST, "插入失败");
    }

    List<DishFlavor> flavors = dto.getFlavors();
    if (flavors != null && ! flavors.isEmpty()) {
      // 设置 dishId
      flavors.forEach(f -> f.setDishId(dish.getId()));
      dishFlavorMapper.insertBatch(flavors); // 批量插入
    }
  }

  @Override
  public PageResult<DishPageQueryVO> pageQuery(DishPageQueryDTO dto) {
    PageHelper.startPage(dto.getPage(), dto.getPageSize());

    Page<DishPageQueryVO> page = dishMapper.pageQuery(dto);
    return new PageResult<>(page.getTotal(), page.getResult());
  }

  @Override
  public void deleteBatch(List<Long> ids) {
    // 起售中的菜品不能删除
    for (Long id : ids) {
      Dish dish = dishMapper.getById(id);
      if (dish.getStatus() == 1) {
        throw new BizException(HttpStatus.BAD_REQUEST, "此菜品正在出售，无法删除！");
      }
    }
    // 当前菜品被套餐关联不能删除
    List<Long> setmealIds = setmealDishMapper.getSetmealDishByDishIds(ids);
    if (setmealIds != null && ! setmealIds.isEmpty()) {
      throw new BizException(HttpStatus.BAD_REQUEST, "当前菜品被套餐关联，无法删除！");
    }

    for (Long id : ids) {
      dishMapper.deleteById(id);

      // 若可能，删除对应的口味
      dishFlavorMapper.deleteByDishId(id);
    }
  }
}
