package com.tuning.service.impl;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.entity.Dish;
import com.Tuning.entity.DishFlavor;
import com.Tuning.exception.BizException;
import com.tuning.mapper.DishFlavorMapper;
import com.tuning.mapper.DishMapper;
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

  @Autowired
  public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
    this.dishMapper = dishMapper;
    this.dishFlavorMapper = dishFlavorMapper;
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
}
