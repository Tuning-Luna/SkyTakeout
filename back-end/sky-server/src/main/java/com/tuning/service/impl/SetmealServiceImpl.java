package com.tuning.service.impl;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.dto.SetmealUpdateDTO;
import com.Tuning.entity.Dish;
import com.Tuning.entity.Setmeal;
import com.Tuning.entity.SetmealDish;
import com.Tuning.exception.BizException;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishItemVO;
import com.Tuning.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.DishMapper;
import com.tuning.mapper.SetmealDishMapper;
import com.tuning.mapper.SetmealMapper;
import com.tuning.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

  final private SetmealMapper setmealMapper;
  final private SetmealDishMapper setmealDishMapper;
  final private DishMapper dishMapper;

  @Autowired
  public SetmealServiceImpl(SetmealMapper setmealMapper,
                            SetmealDishMapper setmealDishMapper,
                            DishMapper dishMapper) {
    this.setmealMapper = setmealMapper;
    this.setmealDishMapper = setmealDishMapper;
    this.dishMapper = dishMapper;
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

  @Override
  @Transactional
  public void deleteBatch(List<Integer> ids) {

    // 检查是否有正在售卖的
    ids.forEach(setmealId -> {
      Setmeal setmeal = setmealMapper.getById(Long.valueOf(setmealId));
      if (setmeal.getStatus() == 1) {
        throw new BizException(HttpStatus.BAD_REQUEST, "该套餐正在售卖，无法删除");
      }
    });


    ids.forEach(setmealId -> {
      // 删除套餐
      setmealMapper.deleteById(Long.valueOf(setmealId));

      // 删除套餐对应的菜品
      setmealDishMapper.deleteBySetmealid(setmealId);
    });
  }

  @Override
  public SetmealVO getByIdWithDish(Long id) {
    Setmeal setmeal = setmealMapper.getById(id);
    List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

    SetmealVO vo = new SetmealVO();
    BeanUtils.copyProperties(setmeal, vo);
    vo.setSetmealDishes(setmealDishes);
    return vo;
  }

  @Override
  @Transactional
  public void update(SetmealUpdateDTO dto) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(dto, setmeal);

    // 更新setmeal
    setmealMapper.update(setmeal);

    // 删除所有关联的setmeal_dish
    setmealDishMapper.deleteBySetmealid(Math.toIntExact(setmeal.getId()));

    // 回填id
    List<SetmealDish> setmealDishes = dto.getSetmealDishes();
    setmealDishes.forEach(setmealDish -> {
      setmealDish.setSetmealId(setmeal.getId());
      setmealDish.setId(null);
    });

    // 重新插入
    setmealDishMapper.insertBatch(setmealDishes);
  }


  @Override
  public void changeStatus(Integer status, Long id) {
    // 如果要起售，先看看里面的菜有没有在停售状态的
    if (status == 1) {
      List<Dish> dishList = dishMapper.getBySetmealId(id);
      if (dishList != null && ! dishList.isEmpty()) {
        dishList.forEach(dish -> {
          if (0 == dish.getStatus()) {
            throw new BizException(HttpStatus.BAD_REQUEST, "此套菜中有菜品未上架，无法上架此套菜");
          }
        });
      }
    }
    Setmeal setmeal = Setmeal.builder()
            .id(id)
            .status(status)
            .build();
    setmealMapper.update(setmeal);
  }

  @Override
  public List<Setmeal> list(Setmeal setmeal) {
    List<Setmeal> list = setmealMapper.list(setmeal);
    return list;
  }

  @Override
  public List<DishItemVO> getDishItemById(Long id) {
    return setmealMapper.getDishItemBySetmealId(id);
  }

}
