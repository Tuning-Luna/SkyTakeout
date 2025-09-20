package com.tuning.service;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.dto.SetmealUpdateDTO;
import com.Tuning.entity.Setmeal;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishItemVO;
import com.Tuning.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
  void insertWithDish(SetmealCreateDTO dto);

  PageResult<SetmealVO> page(SetmealPageQueryDTO dto);

  void deleteBatch(List<Integer> ids);

  void update(SetmealUpdateDTO dto);

  SetmealVO getByIdWithDish(Long id);

  void changeStatus(Integer status, Long id);

  List<Setmeal> list(Setmeal setmeal);

  List<DishItemVO> getDishItemById(Long id);
}
