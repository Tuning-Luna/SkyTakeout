package com.tuning.service.impl;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.entity.Setmeal;
import com.Tuning.entity.SetmealDish;
import com.Tuning.result.PageResult;
import com.Tuning.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.SetmealDishMapper;
import com.tuning.mapper.SetmealMapper;
import com.tuning.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

  final private SetmealMapper setmealMapper;
  private final SetmealDishMapper setmealDishMapper;

  @Autowired
  public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
    this.setmealMapper = setmealMapper;
    this.setmealDishMapper = setmealDishMapper;
  }

  @Override
  @Transactional
  public void insertWithDish(SetmealCreateDTO dto) {
    // 先插入setmeal
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(dto, setmeal);

    setmeal.setStatus(0);
    setmeal.setId(null);

    setmealMapper.insert(setmeal);
    // 回填拿到setmeal_id回填到setmeal_dish表
    Long setmealId = setmeal.getId();
    List<SetmealDish> setmealDishes = dto.getSetmealDishes();

    for (SetmealDish setmealDish : setmealDishes) {
      setmealDish.setSetmealId(setmealId);
      setmealDish.setId(null);
    }

    // 批量插入菜品
    setmealDishMapper.insertBatch(setmealDishes);
  }

  @Override
  public PageResult<SetmealVO> page(SetmealPageQueryDTO dto) {
    PageHelper.startPage(dto.getPage(), dto.getPageSize());

    Page<SetmealVO> page = setmealMapper.pageQuery(dto);
    return new PageResult<>(page.getTotal(), page.getResult());
  }
}
